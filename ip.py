import fileinput, json

lines = ''
for line in fileinput.input():
	lines += line

data = json.loads(lines)
print(data[0]['NetworkSettings']['Networks']['master_default']['IPAddress'])
