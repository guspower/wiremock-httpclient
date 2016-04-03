# WireMock Apache Httpclient test

An attempt to replicate [WireMock issue #381](https://github.com/tomakehurst/wiremock/pull/381)

## Using this project

 * Check it out 

    git clone git@github.com:guspower/wiremock-httpclient.git

 * Run the target server 
 
    ./gradlew -p server run
    
 * Run the test in another console 
 
    ./gradlew
    
## How it works

RunHttpClient.java uses the [WireMock HttpClientFactory](https://github.com/tomakehurst/wiremock/blob/cf6cdefbe8f38da8d53480a5e231586ce46cf018/src/main/java/com/github/tomakehurst/wiremock/http/HttpClientFactory.java) to run get requests in a tight loop for a set period. 
The target server is configured to return 204 for all incoming requests.
Proof of issue replication comes from the detection of a NoHttpResponseException.