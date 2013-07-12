/******************************************************************
 *
 *	CyberHTTP for Java
 *
 *	Copyright (C) Satoshi Konno 2002-2004
 *
 *	File: HTTPRequest.java
 *
 *	Revision;
 *
 *	11/18/02
 *		- first revision.
 *	05/23/03
 *		- Giordano Sassaroli <sassarol@cefriel.it>
 *		- Add a relative URL check to setURI().
 *	09/02/03
 *		- Giordano Sassaroli <sassarol@cefriel.it>
 *		- Problem : Devices whose description use absolute urls receive wrong http requests
 *		- Error : the presence of a base url is not mandatory, the API code makes the assumption that control and event subscription urls are relative
 *		- Description: The method setURI should be changed as follows
 *	02/01/04
 *		- Added URI parameter methods.
 *	03/16/04
 *		- Removed setVersion() because the method is added to the super class.
 *		- Changed getVersion() to return the version when the first line string has the length.
 *	05/19/04
 *		- Changed post(HTTPResponse *) to close the socket stream from the server.
 *	08/19/04
 *		- Fixed getFirstLineString() and getHTTPVersion() no to return "HTTP/HTTP/version".
 *	08/25/04
 *		- Added isHeadRequest().
 *	08/26/04
 *		- Changed post(HTTPResponse) not to close the connection.
 *		- Changed post(String, int) to add a connection header to close.
 *	08/27/04
 *		- Changed post(String, int) to support the persistent connection.
 *	08/28/04
 *		- Added isKeepAlive().
 *	10/26/04
 *		- Brent Hills <bhills@openshores.com>
 *		- Added a fix to post() when the last position of Content-Range header is 0.
 *		- Added a Content-Range header to the response in post().
 *		- Changed the status code for the Content-Range request in post().
 *		- Added to check the range of Content-Range request in post().
 *	03/02/05
 *		- Changed post() to suppot chunked stream.
 *	06/10/05
 *		- Changed post() to add a HOST headedr before the posting.
 *	07/07/05
 *		- Lee Peik Feng <pflee@users.sourceforge.net>
 *		- Fixed post() to output the chunk size as a hex string.
 *
 ******************************************************************/

package lms.foodchainC.http;

/**
 * 
 * This class rappresnet an HTTP <b>request</b>, and act as HTTP client when it
 * sends the request<br>
 * 
 * @author Satoshi "skonno" Konno
 * @author Stefano "Kismet" Lenzi
 * @version 1.8
 * 
 */
public class HTTPRequest extends org.cybergarage.http.HTTPRequest {

}
