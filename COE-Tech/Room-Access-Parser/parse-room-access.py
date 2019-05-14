# Liam Fruzyna 10/9/2018
# C-CURE Room Access Parser
# Formats
# Magnetic: [date] [time] [name] (Card #[card no]) at [room] [In] [reason]
# RFID: [date] [time] (other time?) [name] at [room] [In] (Card#: [card no], CardInt1: [card int]) [reason]

import sys, os, re, platform, subprocess, logging
from tkinter import *

# event data container
class Event:
    def __init__(self, time, admitted, reason, fname, lname, room, cNum, cInt):
        self.time = time.strip()
        self.admitted = str(admitted)
        self.reason = reason.strip()
        self.fname = fname.strip()
        self.lname = lname.strip()
        self.room = room.strip()
        self.cNum = cNum.strip()
        self.cInt = cInt.strip()

    # creates string with data
    def __str__(self):
        return ','.join([','.join(self.time.split()), self.admitted, self.reason, self.fname, self.lname, self.room, self.cNum, self.cInt])

# opens a file using tools for the current platform
def open_file(path):
    if platform.system() == "Windows":
        os.startfile(path)
    elif platform.system() == "Darwin":
        subprocess.Popen(["open", path])
    else:
        subprocess.Popen(["xdg-open", path])

# respond to GUI button press
def submit(event=None):
    # convert path
    inFile = inFileText.get()
    outFile = outFileText.get()

    resultLabel['text'] = inFile
    result = parse(inFile, outFile)
    resultLabel['text'] = result

    # open file when done
    #root.destroy()

    if 'written' in result:
        open_file(outFile)

# clean and process file names
def processFiles(inFile, outFile):
    # react to a file not provided
    if not inFile:
        return None, None
    elif not outFile:
        # determine start of file name
        if platform.system() == "Windows":
            startName = inFile.rfind('\\')
        else:
            startName = inFile.rfind('/')
        if startName <= 0:
            startName = -1
        
        # replace rep with csv
        outFile = inFile[:startName+1] + 'out-' + inFile[startName+1:]
        if outFile.upper().endswith('.REP'):
            outFile = outFile[0:-4]
        outFile = outFile + '.csv'

    # process things like ~
    inFile = os.path.expanduser(inFile)
    outFile = os.path.expanduser(outFile)
    return inFile, outFile

# parse a given file to another given file
def parse(inFile, outFile):
    inFile, outFile = processFiles(inFile, outFile)

    if not inFile:
        return "No input file!"

    # regular expressions for finding the date (first item), and journal switch removal
    reDate = '(\d{2}/\d{2}/\d{4} \d{2}:\d{2}:\d{2} )'    
    reSwitch = '\*\*\* \[Switching to journal volume: \d+\] \*\*\*'

    # read the input file
    with open(inFile, 'r') as f:
        text = f.read()

    # estimate how many entries should be found
    print(str(text.count('Admitted') + text.count('Rejected')), 'estimated entries')
    print(str(text.count('/201')), 'estimated entries')

    # format input for better parsing
    text = text.replace('\n', '') # remove new lines
    text = re.sub(reSwitch, '', text) # remove switching to journal...
    text = ' '.join(text.split()) # remove repetative spaces

    # use dates to determine entries starting point (list contains dates seperately)
    entries = list(filter(None, re.split(reDate, text)))
    print(int(len(entries)/2), 'entries found') # number of entries found

    # create list of events
    events = []
    for i in range(int(len(entries)/2)): # iterate through every other event list item because dates aren't attached
        time = entries[i*2] # even items are dates
        data = entries[i*2+1] # odd items are data
        
        try:
            words = data.split()

            direction = '[In]'
            # check for key words
            if 'at' not in words:
                return 'Missing \"at\" for item ' + str(i) + ' at ' + time
            if '[In]' not in words:
                direction = '[Out]'
                if '[Out]' not in words:
                    return 'Missing \"[In]\" or \"[Out]\" for item ' + str(i) + ' at ' + time
            if 'Admitted' not in words and 'Rejected' not in words:
                return 'Missing admission status for item ' + str(i) + ' at ' + time

            if '[' in words[0]: # remove the extra time if included
                words = words[1:]
            if ']' in words[0] and words[0] != 'Admitted':
                words = words[1:]
            admitted = words[0] == 'Admitted' # the first word is then Admitted/Rejected

            # rejoin everything after the admitted status
            data = ' '.join(words[1:])

            # pull out reason for rejection
            reason = 'Admitted' # "Admitted" if not rejected
            if not admitted:
                reason = data[data.rfind('[')+1:data.rfind(']')] # reason is the last set of brackets

            # remove card data as it is the differentiating item
            if reason != 'Card misread':
                cardStart = data.rfind('(Card')
                cardData = data[cardStart+1:]
                cardEnd = cardStart + cardData.index(')') + 1
                cardData = data[cardStart+1:cardEnd]
                data = data[0:cardStart] + data[cardEnd+1:]
                rfid = ':' in cardData # magnetic readers don't have a colon

            words = data.split()

            atIndex = words.index('at')
            inIndex = words.index(direction)
                
            # room is right before in
            room = words[atIndex + 1]

            if rfid:
                # get card data for rfid scanners
                if reason != 'Card misread':
                    # only not present if card is misread
                    idInfo = cardData.split(':')
                    cNum = idInfo[1].split(',')[0]
                    cInt = idInfo[2]
                else:
                    cNum = 'n/a'
                    cInt = 'n/a'
            else:
                # get card data for magnetic readers
                if reason != 'Card misread':
                    # only not present if card is misread
                    idInfo = cardData.split('#')
                    cNum = idInfo[1]
                    cInt = 'n/a'
                else:
                    cNum = 'n/a'
                    cInt = 'n/a'

            # get name data
            if admitted or reason == 'Clearance' or reason == 'Disabled':
                # not present if card is misread or unknown
                lname = words[0].replace(',', '')
                fname = ' '.join(words[1:atIndex]).replace(',', '')
            else:
                names = reason.split()
                fname = names[0]
                lname = names[1]

            # add event to list
            event = Event(time, admitted, reason, fname, lname, room, cNum, cInt)
            #print(str(event))
            events.append(event)
        except Exception as e:
            logging.exception('message')
            return 'Error on item ' + str(i) + ' at ' + time

    print(len(events), 'events created')

    # write events to file
    with open(outFile, 'w+') as f:
        f.write('Date,Time,Admitted,Reason,First Name,Last Name,Room,Card Number,Card Int\n') # header
        
        # write one per line
        for e in events:
            #print(e)
            f.write(str(e) + '\n')

        return str(len(events)) + ' events written to ' + outFile
    return 'Error writing to ' + outFile

# get command line arguments
args = sys.argv[1:]
if args:
    # get input and output file from command line
    inFile = args[0]
    outFile = args[1]
    result = parse(inFile, outFile)
    print(result)
else:
    # if no arguments open in GUI
    root = Tk()
    root.title('Card Parser')

    # buffer up top
    frame = Frame(master=root, width=300, height=25)
    frame.pack()

    # input label and entry
    inFileLabel = Label(root, text='Input File:')
    inFileLabel.pack()

    inFileText = Entry(root)
    inFileText.pack()
    inFileText.focus_set()

    # output label and entry
    inFileLabel = Label(root, text='Output File:')
    inFileLabel.pack()

    outFileText = Entry(root)
    outFileText.pack()
    
    # submit button (and enter to submit)
    root.bind('<Return>', submit)
    submitButton = Button(root, text='Convert', command=submit)
    submitButton.pack()

    # output text
    resultLabel = Label(root, text='')
    resultLabel.pack()

    # buffer on buttom
    frame = Frame(master=root, width=300, height=25)
    frame.pack()

    root.mainloop()
