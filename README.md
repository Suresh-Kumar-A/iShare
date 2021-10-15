# iShare

## commands to create new DB User
1) CREATE USER 'ishare'@'localhost' IDENTIFIED BY 'P@$s123';
2) GRANT ALL PRIVILEGES ON ishare_db . * TO 'ishare'@'localhost';
3) FLUSH PRIVILEGES;

### Description:
<!-- Rest Api project to perform various user crud operation along with api for sign in auth using jwt tokens -->
______________________________________________________________________________________________________

### RSA Key Generation Steps (using OpenSSL):

#### 1. Generate RSA Private Key (2048 bits)
+) openssl genrsa -out ishare_jwt_privatekey.pem 2048

#### 2. Extract Public Key from Private Key in .pem format | (Optional step)
+) openssl req -new -x509 -key ishare_jwt_privatekey.pem -out ishare_jwt_publickey509.pem -subj '/CN=localhost'

#### 3. Extract Public Key from Private Key in .der format
+) openssl rsa -outform der -in ishare_jwt_privatekey.pem -out ishare_jwt_publickey.der -pubout

#### 4. Convert RSA Private key format (.pem to .der)
+) openssl pkcs8 -topk8 -inform PEM -outform DER -in ishare_jwt_privatekey.pem -out ishare_jwt_privatekey.der -nocrypt


### Lombok support for IDE
Refer this link https://projectlombok.org/setup/overview