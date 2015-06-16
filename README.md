java-ikasan-client
=============

Synopsis
---

```java
IkasanClient ikasanClient = IkasanClient.ikasanClientBuilder("ikasan.example.com")
        .port(8080)      // Default: 4979
        .useSSL(true)    // Default: false
        .verifySSL(true) // Default: true (This option will be activated only when useSSL is true)
        .build();

// send notice
ikasanClient.notice("channel", "message")
        .color(Color.RANDOM)               // Default: Color.YELLOW
        .messageFormat(MessageFormat.HTML) // Default: MessageFormat.TEXT
        .nickname("nick")                  // Default: "ikasan"
        .send();

// send privmsg
ikasanClient.privmsg("channel", "message")
        .color(Color.RANDOM)               // Default: Color.YELLOW
        .messageFormat(MessageFormat.HTML) // Default: MessageFormat.TEXT
        .nickname("nick")                  // Default: "ikasan"
        .send();
```

Description
--

[ikasan](https://github.com/studio3104/ikasan) client for Java (8 or later).

See Also
--

- [ikasan](https://github.com/studio3104/ikasan)
- [ikasan-client](https://github.com/studio3104/ikasan-client)

Author
--

moznion (<moznion@gmail.com>)

License
--

```
The MIT License (MIT)
Copyright © 2015 moznion, http://moznion.net/ <moznion@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the “Software”), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```

