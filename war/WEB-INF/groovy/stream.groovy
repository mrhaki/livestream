import java.text.SimpleDateFormat
import java.text.ParseException
import net.sf.json.groovy.JsonSlurper
import net.sf.json.JSONException
import org.apache.commons.codec.digest.DigestUtils


now = new Date()
lastUpdated = application.getAttribute('lastUpdated')
if (!lastUpdated) {
    lastUpdated = now
    application.setAttribute('lastUpdated', lastUpdated)
}

pipeUrl = 'http://pipes.yahoo.com/pipes/pipe.run?_id=UtVVPkx83hGZ2wKUKX1_0w&_render=json'
if (localMode) {
	pipeUrl = 'http://localhost:8888/sample.json'
}
result = urlFetch.fetch(new URL(pipeUrl))

def resultString = new String(result.content)

resultStringNoPubDate = resultString.replaceFirst(/"pubDate":"\w{3}, \d{2,} \w{3} \d{4} \d{2}:\d{2}:\d{2} [+-]\d{4}",/, '')

etag = DigestUtils.md5Hex(resultStringNoPubDate)
if (headers['If-None-Match'] == etag || headers['If-Modified-Since'] == lastUpdated.time) {
    response.sendError javax.servlet.http.HttpServletResponse.SC_NOT_MODIFIED
    return
}

lastUpdated = now
application.setAttribute 'lastUpdated', lastUpdated

response.setHeader 'ETag', etag
response.setDateHeader 'Last-modified', lastUpdated.time
response.setHeader 'Cache-Control', 'max-age=' + 5 * 60

if (resultString) {
    try {
        def jsonReader = new JsonSlurper()
        def json = jsonReader.parseText(resultString)
        request.setAttribute('json', json)

    } catch (JSONException jsonException) {
        request.setAttribute('error', jsonException.getMessage() + "<pre>${resultString}</pre>")
    }
} else {
    request.setAttribute('error', 'No results found. Try again later.')
}

forward '/stream.gtpl'

