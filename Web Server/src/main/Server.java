package main;

import main.http.HTTPRequest;

public class Server {

	public static void main(String[] args) {
		
		HTTPRequest req = new HTTPRequest("GET / HTTP/1.1\r\nHost: localhost\r\nUser-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0\r\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\nAccept-Language: nl,en-US;q=0.7,en;q=0.3\r\nAccept-Encoding: gzip, deflate\r\nConnection: keep-alive\r\nUpgrade-Insecure-Requests: 1\r\n\r\n");
		System.out.println(req.getValue("Host"));
		
	}

}
