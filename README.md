# WireMock http client test

An attempt to replicate [WireMock issue #381](https://github.com/tomakehurst/wiremock/pull/381)

## Using this project

 * Check it out 

    git clone git@github.com:guspower/wiremock-httpclient.git

 * Run the target server 
 
    ./gradlew -p site run
    
 * Run the test in another console 
 
    ./gradlew
    
## How it works

RunHttpClient.java uses the WireMock HttpClientFactory to run get requests in a tight loop for a set period. 
Proof of issue replication comes from the detection of a NoHttpResponseException.