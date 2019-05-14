# Liam Fruzyna 12/6/2018
# E-LEAD Parser

import sys, os, platform, subprocess
from tkinter import *
import pandas as pd

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
        
        # replace xlsx with csv
        outFile = inFile[:startName+1] + 'out-' + inFile[startName+1:]
        if outFile.upper().endswith('.XLSX'):
            outFile = outFile[0:-5]
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

    # read in excel file
    sheet = pd.read_excel(inFile, dtype={'MUID': str})

    # go through entries
    entries = []
    for i, row in sheet.iterrows():
        id = row['MUID']
        # go through each requirement
        for j in range(2,8):
            req = 'req' + str(j)
            term = row[req]

            # save if there was a term listed
            if isinstance(term, str):
                # get the course name
                if req == 'req2':
                    course = 'GEEN 2961 E-Lead 1'
                elif req == 'req3':
                    course = 'GEEN 3961 E-Lead 2'
                elif req == 'req4':
                    course = 'GEEN 4961 E-Lead 3'
                elif req == 'req5':
                    course = 'GEEN 4998 Capstone'
                elif req == 'req6':
                    course = 'GEEN 3959 EELP'
                elif req == 'req7':
                    course = 'GEEN 3990 PELE'
                else:
                    course = 'UNKNOWN'

                # determine if it is complete
                done = ''
                if isinstance(row[req + 'done'], str):
                    done = 'YES'
                entries.append(id + ',' + course + ',' + term + ',' + done)

    # write events to file
    with open(outFile, 'w+') as f:
        f.write('MUID,COURSE,TERM,COMPLETED\n') # header
        
        # write one per line
        for e in entries:
            #print(e)s
            f.write(str(e) + '\n')

        return str(len(entries)) + ' events written to ' + outFile
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