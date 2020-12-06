import socket, sys
import os

def get_type(path):
    index = path.rfind('/')
    newpath = path[index+1:]
    type =""
    if "." in newpath:
        # file
        index = newpath.rfind(".")
        type = newpath[index+1:]
    return type
def handle_404():
    status_code = 404
    reason = "Not Found"
    res = "HTTP/1.1 %s %s" % (status_code, reason)
    res = res.encode()
    res += b"\r\n"
    return res


def handle_200():
    status_code = 200
    reason = "OK"
    res = "HTTP/1.1 %s %s"% (status_code, reason)
    res = res.encode()
    res += b"\r\n"
    return res


def open_file(path):
    if path.replace(" ", "") == "/":
        path = "/index.html"
    path = ("files" + path).replace(" ", "")
    if os.path.exists(path):
        with open(path, 'rb') as f:
            body = f.read()
    else:
        body = None
    return body


def get_response_line(req):
    filename = req.replace("GET", "").replace("HTTP/1.1", "")  # remove the slash from the request URI
    body = open_file(filename)
    if body is None:
        return handle_404(), None
    else:
        return handle_200(), body


def handle_request(request):
    alive = False
    fields = request.decode().split("\r\n")
    response_line, body = get_response_line(fields[0])
    output = {}
    fields = fields[2:]
    for field in fields:
        if not field:
            continue
        if ":" in field:
            key, value = field.split(':')
            output[key] = value
    if 'keep-alive' in output["Connection"]:
        alive = True
    blank_line = b"\r\n"
    if body is None:
        # if the format is 404 we want to close the connection.
        alive = False
    headers = "Connection:" + ("keep-alive" if alive else "close") + "/r/n"
    if body is not None:
        len_body = len(body)
        headers += "Content-Length: "+"% s" % len_body
        headers = headers.encode()
        headers += blank_line
        content_body = body
        return alive, b"".join([response_line, headers, blank_line, content_body])
    headers = headers.encode()
    headers += blank_line
    return alive, b"".join([response_line, headers])




if __name__ == '__main__':
    TCP_IP = '127.0.0.1'
    TCP_PORT = int(sys.argv[1])
    BUFFER_SIZE = 1024

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((TCP_IP, TCP_PORT))
    s.listen(1)

    while True:
        conn, addr = s.accept()
        print('New connection from:', addr)
        while True:
            conn.settimeout(1)
            try:
                print("befor recv")
                data = conn.recv(BUFFER_SIZE)
                print("after recv")
            except socket.timeout as e:
                print("timeout")
                break
            conn.settimeout(None)
            if not data:
                break
            is_alive, response = handle_request(data)
            conn.sendall(response)
            if not is_alive:
                break
        conn.close()
