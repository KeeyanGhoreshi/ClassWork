import os
import numpy as np
dire = os.walk('/home/keeyan/PycharmProjects/ClassWork/Bioinformatics/files')
a = None
for thing in dire:
    a=thing
dire = a[2]
for d in dire:
    filename = d

    names = d.split('.')
    try:
        names.remove('alnfasta')
        location = 'Test_Result'

    except:
        names.remove('rawfasta')
        location = "Raw"
    name_of_file = '.'.join(names)
    file = open('files/' + filename,'r')
    output = open('/home/keeyan/Desktop/BME4800_Project/' + location +'/' + name_of_file + '.fa','w')
    d = False
    builder = []
    current = ''
    for line in file:
        line = line.replace(':','')

        if d:
            if '>' in line:
                d = False
        if not d:
            if location=='Raw':
                if '>' not in line:
                    current = current + line
                else:
                    if len(current)>0:
                        builder.append(len(current))
                    current = ''
            if 'DIVIDER' in line:
                d = True
            else:
                output.write(line)
    file.close()
    output.close()
    if location=='Raw':
        print(filename)
        print(np.average(builder))