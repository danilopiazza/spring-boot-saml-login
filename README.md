# Installation

On Windows, either install [OpenSSL](https://slproweb.com/products/Win32OpenSSL.html) or use [WSL](https://docs.microsoft.com/windows/wsl/install-win10).

## Relying Party SSL Certificate

1. Create a directory named `certs` under `src/main/resources`
2. Generate an SSL certificate

    - Run:

          openssl req -x509 -newkey rsa:4096 -keyout src/main/resources/certs/rp-private.key -out src/main/resources/certs/rp-certificate.pem -nodes

    - Output:

          Generating a RSA private key
          ...............++++
          ..........................................................................................++++
          writing new private key to 'src/main/resources/certs/rp-private.key'
          -----
          You are about to be asked to enter information that will be incorporated
          into your certificate request.
          What you are about to enter is what is called a Distinguished Name or a DN.
          There are quite a few fields but you can leave some blank
          For some fields there will be a default value,
          If you enter '.', the field will be left blank.
          -----
          Country Name (2 letter code) [AU]:
          State or Province Name (full name) [Some-State]:
          Locality Name (eg, city) []:
          Organization Name (eg, company) [Internet Widgits Pty Ltd]:
          Organizational Unit Name (eg, section) []:
          Common Name (e.g. server FQDN or YOUR name) []:
          Email Address []:

## Identity Provider SSL Certificate

1. Start [SimpleSAMLphp](https://hub.docker.com/r/kristophjunge/test-saml-idp/) by running:

        docker-compose up

2. Navigate to http://localhost:10080/simplesaml/saml2/idp/metadata.php
3. The contents of the `<ds:X509Certificate>` element should match the contents of the `idp-certificate.pem` file under `src/main/resources/certs`

# References

- https://medium.com/disney-streaming/setup-a-single-sign-on-saml-test-environment-with-docker-and-nodejs-c53fc1a984c9
- https://github.com/spring-projects/spring-security/tree/master/samples/boot/saml2login
- https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-saml2
