# Server Room Temperature and Humidity Monitor
# Designed with python2 in mind
# To run add "*/5 * * * * pi ~/ServerRoomMonitor/Launcher.sh" to /etc/crontab
# Jan 11 2019 Liam Fruzyna

import Adafruit_DHT
import smtplib
import datetime
import os

# sensor options
sensor = Adafruit_DHT.AM2302
pin = 4

# measurement bounds
warningTemp = 80
minHumidity = 20
maxHumidity = 60

# files
logFile = 'Logs/DataDump.csv'
emailFile = 'Logs/EmailLog.csv'
warningEmail = 'EmailTemplates/WarningEmail.txt'
dailyEmail = 'EmailTemplates/DailyEmail.txt'

# email options
sendFrom = 'kathleen@www.coetech.marquette.edu'
password = 'Susan1523'
sendTo = ['liam.fruzyna@marquette.edu', 'brad.bonczkiewicz@marquette.edu', 'matthew.derosier@marquette.edu']
serverAddr = '134.48.93.18'
serverPort = 25

# create a new log file each month to prevent the file from getting too large
now = datetime.datetime.now()
parts = logFile.split('.')
logFile = parts[0] + '-' + str(now.month) + '-' + str(now.year) + '.' + parts[1]

# create log files if they don't exist
if not os.path.exists(logFile):
    with open(logFile, 'w'): pass

if not os.path.exists(emailFile):
    with open(emailFile, 'w'): pass

# gets the sensor and returns the humidity and temperature in F
def readTempF():
    humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)
    temperature = temperature * 9/5.0 + 32
    return round(humidity, 2), round(temperature, 2)

# writes a given humidity and temperature to the log file
def logData(humidity, temperature):
    with open(logFile, 'a') as f:
        f.write(now.strftime('%Y-%m-%d,%H:%M:%S') + ',' + str(temperature) + ',' + str(humidity) + '\n')

# writes a given email to the log file
def logEmail(subject):
    with open(emailFile, 'a') as f:
        f.write(now.strftime('%Y-%m-%d,%H:%M:%S') + ',' + subject + '\n')

# sends an email with a given subject and message
def sendEmail(subject, message):
    print 'Attempting connection to ', serverAddr ,'on' , serverPort
    server = smtplib.SMTP(serverAddr, serverPort)
    server.login(sendFrom, password)
    # build email text from subject and message
    text = 'Subject: {}\n\n{}'.format(subject, message)
    server.sendmail(sendFrom, sendTo, text)
    server.quit()

# builds and sends a warning email for a given metric and its value
def sendWarning(metric, value):
    # don't send a warning if one have already been sent today
    if not hasEmailToday(now.strftime('%Y-%m-%d'), metric + ' Warning'):
        print 'Sending warning email for', metric, '\n'
        # open the template and fill in the blanks
        message = open(warningEmail, 'r').read()
        message = message.replace('METRIC', metric)
        message = message.replace('CURR_VAL', str(value))
        subject = metric + ' Warning in the Server Room!'
        logEmail(subject)
        sendEmail(subject, message)

# builds and sends a warning clearing email for a given metric and its value
def clearWarning(metric, value):
    # don't send a warning if one have already been sent today but require a warning to be sent today
    if not hasEmailToday(now.strftime('%Y-%m-%d'), metric + ' Restored') and hasEmailToday(now.strftime('%Y-%m-%d'), metric + ' Warning'):
        print 'Sending warning email for', metric, '\n'
        # open the template and fill in the blanks
        message = open(warningEmail, 'r').read()
        message = message.replace('METRIC', metric)
        message = message.replace('CURR_VAL', str(value))
        subject = metric + ' Restored in the Server Room!'
        logEmail(subject)
        sendEmail(subject, message)

# fetches the uptime in days
def getUpDays():
    with open('/proc/uptime', 'r') as f:
        uptime_secs = float(f.readline().split()[0])
        uptime_str = str(datetime.timedelta(seconds=uptime_secs))
    return uptime_str.split(' ')[0]

# builds and sends a daily update email
def sendDaily():
    tList, hList, days = getToday(now.strftime('%Y-%m-%d'))
    avgT = round(sum(tList) / len(hList), 2)
    avgH = round(sum(hList) / len(hList), 2)
    # open the template and fill in the blanks
    message = open(dailyEmail, 'r').read()
    message = message.replace('NUM_DAYS', getUpDays())
    message = message.replace('DAILY_TEMP', str(avgT))
    message = message.replace('DAILY_HUM', str(avgH))
    message = message.replace('MIN_TEMP', str(min(tList)))
    message = message.replace('MAX_TEMP', str(max(tList)))
    message = message.replace('MIN_HUM', str(min(hList)))
    message = message.replace('MAX_HUM', str(max(hList)))
    subject = 'Daily Report for Server Room'
    sendEmail(subject, message)

# checks if a warning has already been sent today
def hasEmailToday(dateStr, context):
    with open(emailFile, 'r') as f:
        while True:
            line = f.readline()
            cells = line.split(',')
            if ',' in line:
                day = cells[0]
                text = cells[2]
                if day in dateStr and context in text:
                    return True
            else:
                return False

# gets all the data from the current day
def getToday(dateStr):
    tList = []
    hList = []
    days = []
    with open(logFile, 'r') as f:
        while True:
            line = f.readline()
            cells = line.split(',')
            if len(cells) == 4:
                day = cells[0]
                t = float(cells[2])
                h = float(cells[3])
                if day in dateStr:
                    tList.append(t)
                    hList.append(h)
                if not day in days:
                    days.append(day)
            else:
                break
    return tList, hList, days

# fetches and saves the current data
h,t = readTempF()
print 'Temp:', t, 'Hum:', h
logData(h, t)

# checks for warnings and responds appropriately
if h < minHumidity or h > maxHumidity:
    print 'Humidity bounds breached!'
    sendWarning('Humidity', h)
else:
    print 'Humidity restored!'
    clearWarning('Humidity', h)

if t > warningTemp:
    print 'Max temperature breached!'
    sendWarning('Temperature', t)
else:
    print 'Temperature restored!'
    clearWarning('Temperature', t)

# if it is the last test of the day produce and send the daily email
if now.hour == 23 and now.minute >= 55:
    print 'Sending daily email update'
    sendDaily()
