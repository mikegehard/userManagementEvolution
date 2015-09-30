# PACT consumer JS example

Note that running your tests across multiple browsers with one pact mock server will probably 
conflict with each other. You will need to either run them sequentially or 
start multiple pact mock servers.

1. Install node and bower packages:

```
npm install -g karma-cli
npm install -g bower
npm install
bower install
```

2. Start the mock service:

```
node_modules/.bin/pact-mock-service -p 1234 -d pacts
```

3. Start the karma tests:

```
karma start
```

This will generate the PACT file.
