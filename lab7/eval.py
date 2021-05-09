import os
import sys
a = sys.argv[1]
os.system('ant make-jar')
os.chdir('./jars')
os.system(f'java -jar ./processor.jar ./../src/configuration/config.xml ./../objectfiles/{a}.stats ./../objectfiles/{a}.out')
os.system(f'cat ./../objectfiles/{a}.stats')