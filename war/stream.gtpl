<%
import java.text.SimpleDateFormat
import java.text.ParseException

def parseActivityDate = { dateString ->
    def result
    // Remove semicolon in timezone part.
    dateString = dateString.replaceAll(/(\d{2}):(\d{2})$/, '$1$2')
    def parsers = [ 
        new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US),
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US),
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
    ]
    for (SimpleDateFormat parser : parsers) {
        try {
            result = parser.parse(dateString)
            break
        } catch (ParseException e) {
            continue
        }
    }
    result        
}
%>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <title>Livestream of mrhaki</title>

    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <link rel="stylesheet" media="all" href="http://www.mrhaki.com/css/resets.css"/>
    <link rel="stylesheet" media="all" href="http://www.mrhaki.com/css/default.css"/>
    <link rel="stylesheet" media="all" href="http://www.mrhaki.com/css/13col.css"/>
    <link rel="stylesheet" media="all" href="http://www.mrhaki.com/css/3col.css"/>
    <link rel="stylesheet" media="all" href="http://www.mrhaki.com/css/5col.css"/>
    <link rel="stylesheet" media="all" href="http://www.mrhaki.com/css/iphone4.css"/>
    <link rel="stylesheet" media="all" href="/css/main.css"/>

    <link rel="shortcut icon" href="/favicon.ico">

    <meta name="viewport" content="width=device-width; initial-scale=1"/>
    <!-- Add "maximum-scale=1" to fix the weird iOS auto-zoom bug on orientation changes. -->
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js"></script>
    <script src='/js/humane.js' type='text/javascript'> </script>
    <script src="/js/google-analytics.js"></script>
</head>

<body>

    <header>
        <div id="header_logo">
            <a href="http://www.mrhaki.com">
                <img src="http://www.mrhaki.com/images/haki-logo-black-128.png" width="64" height="64"
                    alt="Logo haki" title="Logo haki"/>
            </a>
        </div>
        <nav id="headernav">
            <ul>
                <li><a href="http://mrhaki.blogspot.com">Blog</a></li>
                <li><a href="http://github.com/mrhaki">Github</a></li>
                <li><a href="http://stream.mrhaki.com">Livestream</a></li>
                <li><a href="http://www.mrhaki.com/about/">About</a></li>
            </ul>
        </nav>
    </header>

    <div id="body_content">

        <% if (request.getAttribute('error')) { %>
            ${request.getAttribute('error')}
        <% } else { %>
            <p>
            This livestream is built with 
            <a href="http://gaelyk.appspot.com/">Gaelyk</a> 0.6.1 and
            deployed on the
            <a href="http://code.google.com/appengine/">Google AppEngine platform</a>.
            The sources of the livestream are first processed
            with a 
            <a href="http://pipes.yahoo.com/pipes/pipe.info?_id=UtVVPkx83hGZ2wKUKX1_0w">Yahoo Pipe</a>.
            The resulting JSON is parsed by a Groovlet and
            the result is displayed on this page.
            <br />
            Sources are available at <a href="https://github.com/mrhaki/livestream">Github</a>.
            </p>
        
            <ul id="activities">

            <% request.getAttribute('json').value.items.each { item -> 
                   def itemDate = parseActivityDate(item.pubDate) 
            %>
                <li class="item ${item.source}">
                    <h2 class="date-header">
                        ${itemDate.format("dd MMM yyyy")},
                        ${itemDate.format("HH:mm")}
                    </h2>
                    <p>
                    ${item.title}
                    </p>
                    <div class="post-footer">
                        <span class="item-humanedate" title="${itemDate?.format("yyyy-MM-dd'T'HH:mm:ss")}">...</span>
                        <span class="item-source ${item.source}">from <a href="${item.link}">${item.source}</a></span>
                    </div>
                </li>
            <% } %>
            </ul>
            
            <script type='text/javascript'>
            \$(document).ready(function() {
                \$("span.item-humanedate").humane_dates();
                setInterval(function() {
                    \$("span.item-humanedate").humane_dates();
                }, 5000);
            });
            </script>
        <% } %>

    </div>

    <footer>
        <nav id="footer_links">
            <h3>Links</h3>
            <ul>
                <li><a href="http://www.mrhaki.com/">Website</a></li>
                <li><a href="http://mrhaki.blogspot.com">Blog</a></li>
                <li><a href="http://github.com/mrhaki">Github</a></li>
                <li><a href="http://stream.mrhaki.com">Livestream</a></li>
                <li><a href="http://www.mrhaki.com/about/">About</a></li>
            </ul>
        </nav>
        <nav id="friend_links">
            <h3>Link to friends</h3>
            <ul>
                <li><a href="http://www.kralencreatie.nl">KralenCreatie</a></li>
                <li><a href="http://www.nagelcreatie.nl">NagelCreatie</a></li>
                <li><a href="http://www.tk1450.com/">The adventures of TK-1450</a></li>
                <li><a href="http://www.pixeldam.net">Pixeldam</a></li>
                <li><a href="http://www.x-panded.com/">Xpanded</a></li>
                <li><a href="http://www.drbob42.com/">DrBob42</a></li>
            </ul>
        </nav>
        <nav id="connect_links">
            <h3>Connect</h3>
            <ul>
                <li><a href="http://www.twitter.com/mrhaki">Twitter</a></li>
                <li><a href="http://nl.linkedin.com/in/mrhaki">LinkedIn</a></li>
                <li><a href="http://www.facebook.com/mrhaki">Facebook</a></li>
                <li><a href="http://www.delicious.com/mrhaki">Delicious</a></li>
                <li><a href="http://www.mrhaki.com/about/mrhaki.vcf">Contact</a></li>
            </ul>
        </nav>
        <div class="clearfix"></div>
        <p id="copyright">
            &copy; 2011
            <a href="http://iam.mrhaki.com">Hubert A. Klein Ikkink</a>
        </p>
    </footer>

</body>

</html>
