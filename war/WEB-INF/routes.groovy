get "/", forward: "/stream.groovy"
get "/index",  forward: "/stream.groovy"
get "/index.html", forward: "/stream.groovy"

get "/favicon.ico", redirect: "/images/favicon.ico"