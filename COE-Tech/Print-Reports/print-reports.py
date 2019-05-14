# Liam Fruzyna
# May 2019

# Dependencies:
# Python, python.com/downloads
# PIP, bootstrap.pypa.io/get-pip.py
# Pandas, run "py -m pip install pandas"
# xlrd, run "py -m pip install xlrd"
# openpyxl, run "py -m pip install openpyxl"

# Input File Requirements:
# The script expects the name of a folder (relative to the script location or absolute)
# containing any number of excel files named as: GFC.YYYY.MM.xlsx
# The excel files should only have one sheet with 19 columns.
# The columns must be in the following order, but don't require these exact names:
# CC_Name, PrinterName, UserName, Mthy_LeaseCost, Printer_Total_Pages, LeaseCost, LeasePercent, ColorPrintPages, ColorCopyPages, ColorCost, 
# BWPrintPages, BWCopyPages, BWCost, DuplexPrintPages, FaxScanPages, AmountPaid, BillablePages, BillableCharge, TotalCharge
# The script will add 5 additional columns named:
# Date, ColorTotalPages, BWTotalPages, PaperCost, TotalCost

# Output File:
# The output file will default to output.xlsx if no name is provided.
# If the provided file does not end in .xlsx it will be added to the end.

# Instructions:
# Double-click to run.
# In GUI enter paths for folder containing reports to be merged and output file.

# Preferences:
LEASE_COST = 41.868
PRINTER_NAME = 'DL146-iRC5235'
PAPER_COST = 0.00983

depts = ['1400', '1410', '1420', '1430', '1440']
lastDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]

import os, sys, re
import pandas as pd
import tkinter as tk

# check if a lease was properly charged
def checkLease(df, deptCode):
    return ((df['CC_Name'] == deptCode) & (df['LeaseCost'] == LEASE_COST)).any()

# process a single monthly report
def processReport(folder, fName):
    # attempt to get the month from the file name
    search = re.search('GFC\.([0-9]{4}?)\.([0-9]{1,2}?)\.xlsx', fName)
    if search:
        month = search.group(2)
        year = search.group(1)
    else:
        return
    print('Processing', month, year)

    if len(month) == 1:
        month = '0' + month
    day = lastDays[int(month)-1]

    # read in the 3rd sheet of the excel file
    df = pd.read_excel(folder + '/' + fName)

    # unify column names
    columns = ['CC_Name', 'PrinterName', 'UserName', 'Mthy_LeaseCost', 'Printer_Total_Pages', 'LeaseCost', 'LeasePercent', 'ColorPrintPages', 'ColorCopyPages', 'ColorCost', 'BWPrintPages', 'BWCopyPages', 'BWCost', 'DuplexPrintPages', 'FaxScanPages', 'AmountPaid', 'BillablePages', 'BillableCharge', 'TotalCharge']
    if len(df.columns) > len(columns):
        columns += [''] * (len(df.columns) - len(columns))
    df.columns = columns

    # (0) Add a column for the first day of the month
    date = month + '/' + str(day) + '/' + year
    df['Date'] = date

    # (1) Filter out OCOE cost centers
    df = df[df['CC_Name'].str.contains('[(01)(06)]?-?014[0-9]{2}-[0]{5}')]
    
    # (2) Remove leading and following numbers
    df['CC_Name'] = df['CC_Name'].str.split('-').str[-2].str[1:]

    # (3) Check that all departments are there
    for dept in depts:
        if not checkLease(df, dept):
            print('Incorrect lease charge for', dept)
            return
    
    # (4) Remove lease costs and adjust total charge
    df.loc[df['LeaseCost'] == LEASE_COST, 'TotalCharge'] -= LEASE_COST
    df.loc[df['LeaseCost'] == LEASE_COST, 'LeaseCost'] = 0

    # (5) Add lease cost for each department
    for dept in depts:
        row = pd.DataFrame(data={'CC_Name': [dept], 'PrinterName': [PRINTER_NAME], 'UserName': ['ENG LEASE COST'], 'LeaseCost': [LEASE_COST], 'TotalCharge': [LEASE_COST], 'ColorPrintPages': [0], 'ColorCopyPages': [0], 'BWPrintPages': [0], 'BWCopyPages': [0], 'Date': [date]})
        df = df.append(row)
 
    #df.to_excel(month + '-' + year + '.xlsx', index=False)    
    return df

def start(folder, outFile):
	# process every report in the folder
	reports = [processReport(folder, f) for f in os.listdir(folder) if os.path.isfile(os.path.join(folder, f)) and f.endswith('.xlsx')]

	# (6) Combine all reports
	df = pd.concat(reports)

	# (9) Add color and bw totals
	df['ColorTotalPages'] = df['ColorPrintPages'] + df['ColorCopyPages']
	df['BWTotalPages'] = df['BWPrintPages'] + df['BWCopyPages']

	# (7) Calculate paper cost
	df['PaperCost'] = (df['ColorTotalPages'] + df['BWTotalPages']) * PAPER_COST
		
	# (8) Calculate total cost
	df['TotalCost'] = df['TotalCharge'] + df['PaperCost']

	# reorder columns
	df = df[['CC_Name', 'PrinterName', 'UserName', 'Date', 'Mthy_LeaseCost', 'Printer_Total_Pages', 'LeaseCost', 'LeasePercent', 'ColorPrintPages', 'ColorCopyPages', 'ColorTotalPages', 'ColorCost', 'BWPrintPages', 'BWCopyPages', 'BWTotalPages', 'BWCost', 'DuplexPrintPages', 'FaxScanPages', 'AmountPaid', 'BillablePages', 'BillableCharge', 'TotalCharge', 'PaperCost', 'TotalCost']]

	# writing to file
	if not outFile.endswith('.xlsx'):
		outFile += '.xlsx'
	df.to_excel(outFile, index=False)    

def submit(event=None):
    # convert paths
    folder = os.path.expanduser(inFileText.get())
    outFile = os.path.expanduser(outFileText.get())
    if outFile == '':
        outFile = 'output.xlsx'

    if os.path.exists(folder):
        resultLabel['text'] = 'Working...'
        start(folder, outFile)
        resultLabel['text'] = 'File written to ' + outFile
    else:
        resultLabel['text'] = 'Input folder not found'

#
# Main execution starts here
#

args = sys.argv[1:]
if len(args) == 2:
    # get input and output file from command line
    folder = os.path.expanduser(args[0])
    outFile = os.path.expanduser(args[1])

    if os.path.exists(folder):
        start(folder, outFile)
        print('Complete!')
    else:
        print('Folder not found!')
else:
    # if no arguments open in GUI
    root = tk.Tk()
    root.title('Print Reports')

    # buffer up top
    frame = tk.Frame(master=root, width=300, height=25)
    frame.pack()

    # input label and entry
    inFileLabel = tk.Label(root, text='Input Folder:')
    inFileLabel.pack()

    inFileText = tk.Entry(root)
    inFileText.pack()
    inFileText.focus_set()

    # output label and entry
    inFileLabel = tk.Label(root, text='Output File:')
    inFileLabel.pack()

    outFileText = tk.Entry(root)
    outFileText.pack()
    
    # submit button (and enter to submit)
    root.bind('<Return>', submit)
    submitButton = tk.Button(root, text='Convert', command=submit)
    submitButton.pack()

    # output text
    resultLabel = tk.Label(root, text='')
    resultLabel.pack()

    # buffer on buttom
    frame = tk.Frame(master=root, width=300, height=25)
    frame.pack()

root.mainloop()
