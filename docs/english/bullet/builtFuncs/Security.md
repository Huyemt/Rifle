# [Return to the tutorial](../README.md)
# [Returns a list of built-in functions](../builtFunc.md)
***

# md5
## Introduce
MD5 encryption
## Paramters
| Name    | Type                 | Description                        | Optional |
|:--------|:---------------------|:-----------------------------------|:---------|
| content | Number, List, String | Content that needs to be encrypted |          |
## Return value
`String` or `List`
## Example
```bullet
// 0192023a7bbd73250516f069df18b500
println( md5("admin123") )

// 6412121cbb2dc2cb9e460cfee7046be2
println( md5( 10086 ) )

// 26ef2e2349d89cac9db0bcdc6294c530
println( md5( 10086.999 ) )

// 25d36d1ab8f512cb2518a5b8dfd23dee
println( md5( 139875734751985017895.198729841820941725091820948109 ) )

// ["0192023a7bbd73250516f069df18b500","6412121cbb2dc2cb9e460cfee7046be2","26ef2e2349d89cac9db0bcdc6294c530","25d36d1ab8f512cb2518a5b8dfd23dee"]
println( md5( ["admin123", 10086, 10086.999, 139875734751985017895.198729841820941725091820948109] ) )
```

# sha1
## Introduce
SHA1 encryption
## Paramters
| Name    | Type                 | Description                        | Optional |
|:--------|:---------------------|:-----------------------------------|:---------|
| content | Number, List, String | Content that needs to be encrypted |          |
## Return value
`String` or `List`
## Example
```bullet
// f865b53623b121fd34ee5426c792e5c33af8c227
println( sha1("admin123") )

// 7205db181158aa3dfc55d22fe521f447ca2e5777
println( sha1( 10086 ) )

// d22af3c8923049c1ae87c295e269cf10a54f9a8a
println( sha1( 10086.999 ) )

// d22af3c8923049c1ae87c295e269cf10a54f9a8a
println( sha1( 139875734751985017895.198729841820941725091820948109 ) )

// ["f865b53623b121fd34ee5426c792e5c33af8c227","7205db181158aa3dfc55d22fe521f447ca2e5777","8e66181dccfe18806cf00e02557e5a86f29dcc33","d22af3c8923049c1ae87c295e269cf10a54f9a8a"]
println( sha1( ["admin123", 10086, 10086.999, 139875734751985017895.198729841820941725091820948109] ) )
```

# sha256
## Introduce
SHA256 encryption
## Paramters
| Name    | Type                 | Description                        | Optional |
|:--------|:---------------------|:-----------------------------------|:---------|
| content | Number, List, String | Content that needs to be encrypted |          |
## Return value
`String` or `List`
## Example
```bullet
// 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9
println( sha256("admin123") )

// 9646f275f10ae73f70fa297fef85e62b5accd3a38284eb0a64b8203e12dd1373
println( sha256( 10086 ) )

// ebf93f1327795c1f9aea03bdcce1ec70493ef7984a18cc679d8494964960228f
println( sha256( 10086.999 ) )

// f87bacb695339144a72cee3636528b5f5f242e4aa07c0a33c65aa63a60df6b0f
println( sha256( 139875734751985017895.198729841820941725091820948109 ) )

// ["240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9","9646f275f10ae73f70fa297fef85e62b5accd3a38284eb0a64b8203e12dd1373","ebf93f1327795c1f9aea03bdcce1ec70493ef7984a18cc679d8494964960228f","f87bacb695339144a72cee3636528b5f5f242e4aa07c0a33c65aa63a60df6b0f"]
println( sha256( ["admin123", 10086, 10086.999, 139875734751985017895.198729841820941725091820948109] ) )
```

# sha512
## Introduce
SHA512 encryption
## Paramters
| Name    | Type                 | Description                        | Optional |
|:--------|:---------------------|:-----------------------------------|:---------|
| content | Number, List, String | Content that needs to be encrypted |          |
## Return value
`String` or `List`
## Example
```bullet
// 7fcf4ba391c48784edde599889d6e3f1e47a27db36ecc050cc92f259bfac38afad2c68a1ae804d77075e8fb722503f3eca2b2c1006ee6f6c7b7628cb45fffd1d
println( sha512("admin123") )

// a3b36ab5d95228503552a71c49ed8538d2fd272ecec3687d56da6a6a9eb2629e4d71daa2c60317caad5c73e582340b1b8b953891222317b7af4745909de3ad5
println( sha512( 10086 ) )

// 16ad76ad880f54d6d95ad5169e6f9d962365a9aec7bfcff4e84dcb70b54e9f0c393eb5f4062154df3986475928f75241b56b24e7cedc90e8bd21ff5f29df9d1c
println( sha512( 10086.999 ) )

// e649ea9f76fe3c658d98d046f72c88248eec1f42e60a1228ebbf8c726e769ff3ce0caea5d3ec421e78fd525241358173c51256a40dce7fd403ac376e01ae24f4
println( sha512( 139875734751985017895.198729841820941725091820948109 ) )

// ["7fcf4ba391c48784edde599889d6e3f1e47a27db36ecc050cc92f259bfac38afad2c68a1ae804d77075e8fb722503f3eca2b2c1006ee6f6c7b7628cb45fffd1d","a3b36ab5d95228503552a71c49ed8538d2fd272ecec3687d56da6a6a9eb2629e4d71daa2c60317caad5c73e582340b1b8b953891222317b7af4745909de3ad5","16ad76ad880f54d6d95ad5169e6f9d962365a9aec7bfcff4e84dcb70b54e9f0c393eb5f4062154df3986475928f75241b56b24e7cedc90e8bd21ff5f29df9d1c","e649ea9f76fe3c658d98d046f72c88248eec1f42e60a1228ebbf8c726e769ff3ce0caea5d3ec421e78fd525241358173c51256a40dce7fd403ac376e01ae24f4"]
println( sha512( ["admin123", 10086, 10086.999, 139875734751985017895.198729841820941725091820948109] ) )
```

# aesE
## Introduce
AES encryption
## Paramters
| Name    | Type           | Description                        | Optional |
|:--------|:---------------|:-----------------------------------|:---------|
| content | String, Number | Content that needs to be encrypted |          |
| key     | String         | Key                                |          |
| mode    | Number         | Encryption mode                    | √        |
| padding | Number         | Padding mode                       | √        |
| iv      | String         | Offset                             | √        |
<br><br>
`mode`

| Value | Meaning |
|:------|:--------|
| 0     | CBC     |
| 1     | ECB     |
| 2     | CTR     |
| 3     | OFB     |
| 4     | CFB     |
<br><br>
`padding`

| Value | Meaning    |
|:------|:-----------|
| 0     | NO_PADDING |
| 1     | PKCS5      |
| 2     | ISO10126   |
## Return value
`String`
## Example
```bullet
content := "Hello, World"
key := "BestLangBullet77"
iv := "best_lang_bullet"
mode := 0

r := aesE( content, key=key, mode=mode, iv=iv )

// Lh85i/g7jKoiVNGco/zLeA==
println( r )
```

# aesD
## Introduce
AES decryption
## Paramters
| Name    | Type   | Description             | Optional |
|:--------|:-------|:------------------------|:---------|
| content | String | Content to be decrypted |          |
| key     | String | Private key             |          |
| mode    | Number | Decryption mode         | √        |
| padding | Number | Padding mode            | √        |
| iv      | String | Offset                  | √        |
<br><br>
`mode`

| Value | Meaning |
|:------|:--------|
| 0     | CBC     |
| 1     | ECB     |
| 2     | CTR     |
| 3     | OFB     |
| 4     | CFB     |
<br><br>
`padding`

| Value | Meaning    |
|:------|:-----------|
| 0     | NO_PADDING |
| 1     | PKCS5      |
| 2     | ISO10126   |
## Return value
`String`
## Example
```bullet
content := "Lh85i/g7jKoiVNGco/zLeA=="
key := "BestLangBullet77"
iv := "best_lang_bullet"
mode := 0

r := aesD( content, key=key, mode=mode, iv=iv )

// Hello, World
println( r )
```

# rsaE
## Introduce
RSA encryption
## Paramters
| Name    | Type           | Description                        | Optional |
|:--------|:---------------|:-----------------------------------|:---------|
| content | String, Number | Content that needs to be encrypted |          |
| key     | String         | Public key                         | √        |
| padding | Number         | Padding mode                       | √        |
<br><br>
`padding`

| Value | Meaning    |
|:------|:-----------|
| 0     | NO_PADDING |
| 1     | PKCS1      |
| 2     | PKCS1_OAEP |
## Return value
`String`
<br>
or
<br>
`Dictionary`

| Name       | Description  | Value类型 |
|:-----------|:-------------|:--------|
| result     | Result       | String  |
| publicKey  | Public key   | String  |
| privateKey | Private key  | String  |
| padding    | Padding mode | Number  |
## Example
```bullet
content := "Hello, World"

r := rsaE( content )

// MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCOspNsAo13vbJnKUP6OWikFMzXCGbaXpzX6ZESXVAR6x4O3AJZio4HEZYQ6+RgtqfYE0JV3Q5OgkyRWNIrJa4EP5x+RMtet3M1R37Kjgq9Tvb6G5eF4ZS7hMC4gKWmoyPEw6DFZoQi37c8gHKF5z6mK/4JmNgPhNFZrEz0beEISS5aCtxXVCnNqeR/+2UEjQnCYUJDYt2ls68l05pFlJXJ2J+j5TUX+ApW1oJc6R+gLebl1pyFZkKgaLVvCGai1lMkpyHedYuyUUr8v1v3ygGnLFmTFDcUJ2V6NjkVNI4exjQK0jcZHScwTSkUe6CFKvHKqFCLtRObVBpAit/4TQzNAgMBAAECggEAcXZQfhtxfPpatmg7YuTu7TiUv44wqgV81Lkk3uNPXVAD8HDDdYsT707ucbn/N19FCiwWHMvOKmK0mwVy51foi/xAZK4yvrdDZePZTxmuNGRrQOdbjdqWNpwR2SKBlIQ3VqbuWMdw3YHg4ryHUy1RxTNJpTvg4EYNaC32aoxL2BeYEVEfiHlKG5kRpUzThNcnp9WAZSd18cC2L/rOMCgctV7H/TWhT/Dq4yjFM18tFTA31exZl2dMdftbOkTrNyWxbWEGC7ir30Y+1WnQFOInt6jJqoPkYmJHpkaYyd5YFxNsbLct2x1wolt+wF9Ng2FNkRAk3Ne209OMIPw6G5z2yQKBgQDG4YahKQlkpRapU5KiDbH/fHn5b/nn92Ejfmej9WRE2vmBYDaWm9bQIwqSEvBzYhNFFNVK4VJA9XWXugBUSd9onJDzg3b1QwxwY5+w+oOwppkJWhOOYcAz73tSFumKtWcau2EJHmSvMMQOjOE5p12sMAHU4EMFEATk0CQt10f4twKBgQC3rjxdP1NBpD3Dk8eVCsm5sz6K3mU9hKFTXG38QLthUwisQDcy8EE0cbzJQIcAOmI29v+6xgpCqocwMMWB3/13grM7jBkgwadzcXmkLUzHT3h+USBDRKuMZ3m3MMy33gBBzMvOVnksyTXglxPsLHWxl8rlcd20hsIn7TOFGaA6mwKBgQCXmSmqh0FluoG/qLjJVm8sNJI1lSmFrIgbKiuOS2uXIOO3vsNdooToOw2/szgee8/8hJjhb0fnxDNS6LF/jqABefbz6G4xHl7I/OepXAwuB4/4FPV7Pv/nltEGDfkPhp+FPPgGn2hYMnAAN1snO3Cn5CBBSIFNxpw0XoR1fPibmQKBgQCEj+jc21D49NHmjobh19FShjxC+NJUHZ5YjUKLZSRWzxhZSFcOGjrU4KkBDeLglUJArO5PG5JYHr5GV0yTuNYzJE66URfpfhmdxW2mwVLCHWa2s1H3el3cjOlY/o0gvcWtt2H3Xo1Bd0288wvbzRJ7wzMZeJ4rJX6GVjhyfNYYtQKBgQCpy+0s7wTpR3qME6y4yiXyAB6hDTk6sxLj6ZrDaO4WGfQTbzwPfJ+tlx5BpQmXV7+Ccow+KP9BwvH96eXjT7TZJzb79WCFmpkQSKIhCjKwhHnm80POvLGaiY0Zuj9JpVZOzxCM5Iz+yFAaSCeBIGFXOZ13Mndta4aMAMxoy7u7Kw==
println( r['privateKey'] )
// MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjrKTbAKNd72yZylD+jlopBTM1whm2l6c1+mREl1QEeseDtwCWYqOBxGWEOvkYLan2BNCVd0OToJMkVjSKyWuBD+cfkTLXrdzNUd+yo4KvU72+huXheGUu4TAuIClpqMjxMOgxWaEIt+3PIByhec+piv+CZjYD4TRWaxM9G3hCEkuWgrcV1Qpzankf/tlBI0JwmFCQ2LdpbOvJdOaRZSVydifo+U1F/gKVtaCXOkfoC3m5dachWZCoGi1bwhmotZTJKch3nWLslFK/L9b98oBpyxZkxQ3FCdlejY5FTSOHsY0CtI3GR0nME0pFHughSrxyqhQi7UTm1QaQIrf+E0MzQIDAQAB
println( r['publicKey'] )
// 1
println( r['padding'] )

// LkJLtTv9IZ8R3Su6y3+N0cXUDjFUz8M9c5poskQuaEY2Ga6vblSMtV1QGeFTvwx3GOaVuSMGkg1nw/YrXHg0tlPMZa2UIMPv3PFlE/AAJLu/rtD122GmHxKWqXvpFBD9iPExf7VYFNwG+Z0zOvkvLEVFplPtKMD8+iIi2ubhKG6zL6tqExlItI1VlgcZ5LYJSvZrsQcDPumRcyC7A0KsEiqn/RGA1cD1Xf3IAWw+zzJb7aYnSWkVI0jIlVWI3hWTFn4d7LQkInSH50SelLIdZq88yKjrr9x5qJsZOXqLMw+OlyhVCeaI6/69Z35wfgTKwxPGpttnJIP3gxHcaiuFMg==
println( r['result'] )

// T5r3jhxWi5WM78SMzgA4tDgVIi2kKs6nyNd3JCHWRnTYr4mvZE+JYBQkoBwVC2mLqorJCzU/2OagNkcbmShbZ4MDnonvZY06K4hIMQjqStnQV5QDDnkuXfVShB9BLIzPQFICE1dnutqWiWOtZ9rHRbmYrtFTD9fxI5B8zCM9hLoEZ5b1Y/RkpMJOL5WXNSsHqeas6BwQjDbhSptwdjCj08CyznfhPluYryrl/3gICKA/KsiA9QZI4LgcL2y1uelAYtjjAwG2hfK/mMtcFfOyipYYqLIfuDjEhGwP8Ng/+y5drjcKJLJa8r+YrI9zi9y5pohPccn6swSO2xxF9hXp8w==
println( rsaE( "Bullet", r['publicKey'] ) )
```

# rsaD
## Introduce
RSA decryption
## Paramters
| Name    | Type   | Description             | Optional |
|:--------|:-------|:------------------------|:---------|
| content | String | Content to be decrypted |          |
| key     | String | Private Key             |          |
| padding | Number | Padding mode            | √        |
<br><br>
`padding`

| Value | Meaning    |
|:------|:-----------|
| 0     | NO_PADDING |
| 1     | PKCS1      |
| 2     | PKCS1_OAEP |
## Return value
`String`
## Example
```bullet
privateKey := "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCOspNsAo13vbJnKUP6OWikFMzXCGbaXpzX6ZESXVAR6x4O3AJZio4HEZYQ6+RgtqfYE0JV3Q5OgkyRWNIrJa4EP5x+RMtet3M1R37Kjgq9Tvb6G5eF4ZS7hMC4gKWmoyPEw6DFZoQi37c8gHKF5z6mK/4JmNgPhNFZrEz0beEISS5aCtxXVCnNqeR/+2UEjQnCYUJDYt2ls68l05pFlJXJ2J+j5TUX+ApW1oJc6R+gLebl1pyFZkKgaLVvCGai1lMkpyHedYuyUUr8v1v3ygGnLFmTFDcUJ2V6NjkVNI4exjQK0jcZHScwTSkUe6CFKvHKqFCLtRObVBpAit/4TQzNAgMBAAECggEAcXZQfhtxfPpatmg7YuTu7TiUv44wqgV81Lkk3uNPXVAD8HDDdYsT707ucbn/N19FCiwWHMvOKmK0mwVy51foi/xAZK4yvrdDZePZTxmuNGRrQOdbjdqWNpwR2SKBlIQ3VqbuWMdw3YHg4ryHUy1RxTNJpTvg4EYNaC32aoxL2BeYEVEfiHlKG5kRpUzThNcnp9WAZSd18cC2L/rOMCgctV7H/TWhT/Dq4yjFM18tFTA31exZl2dMdftbOkTrNyWxbWEGC7ir30Y+1WnQFOInt6jJqoPkYmJHpkaYyd5YFxNsbLct2x1wolt+wF9Ng2FNkRAk3Ne209OMIPw6G5z2yQKBgQDG4YahKQlkpRapU5KiDbH/fHn5b/nn92Ejfmej9WRE2vmBYDaWm9bQIwqSEvBzYhNFFNVK4VJA9XWXugBUSd9onJDzg3b1QwxwY5+w+oOwppkJWhOOYcAz73tSFumKtWcau2EJHmSvMMQOjOE5p12sMAHU4EMFEATk0CQt10f4twKBgQC3rjxdP1NBpD3Dk8eVCsm5sz6K3mU9hKFTXG38QLthUwisQDcy8EE0cbzJQIcAOmI29v+6xgpCqocwMMWB3/13grM7jBkgwadzcXmkLUzHT3h+USBDRKuMZ3m3MMy33gBBzMvOVnksyTXglxPsLHWxl8rlcd20hsIn7TOFGaA6mwKBgQCXmSmqh0FluoG/qLjJVm8sNJI1lSmFrIgbKiuOS2uXIOO3vsNdooToOw2/szgee8/8hJjhb0fnxDNS6LF/jqABefbz6G4xHl7I/OepXAwuB4/4FPV7Pv/nltEGDfkPhp+FPPgGn2hYMnAAN1snO3Cn5CBBSIFNxpw0XoR1fPibmQKBgQCEj+jc21D49NHmjobh19FShjxC+NJUHZ5YjUKLZSRWzxhZSFcOGjrU4KkBDeLglUJArO5PG5JYHr5GV0yTuNYzJE66URfpfhmdxW2mwVLCHWa2s1H3el3cjOlY/o0gvcWtt2H3Xo1Bd0288wvbzRJ7wzMZeJ4rJX6GVjhyfNYYtQKBgQCpy+0s7wTpR3qME6y4yiXyAB6hDTk6sxLj6ZrDaO4WGfQTbzwPfJ+tlx5BpQmXV7+Ccow+KP9BwvH96eXjT7TZJzb79WCFmpkQSKIhCjKwhHnm80POvLGaiY0Zuj9JpVZOzxCM5Iz+yFAaSCeBIGFXOZ13Mndta4aMAMxoy7u7Kw=="
content1 := "LkJLtTv9IZ8R3Su6y3+N0cXUDjFUz8M9c5poskQuaEY2Ga6vblSMtV1QGeFTvwx3GOaVuSMGkg1nw/YrXHg0tlPMZa2UIMPv3PFlE/AAJLu/rtD122GmHxKWqXvpFBD9iPExf7VYFNwG+Z0zOvkvLEVFplPtKMD8+iIi2ubhKG6zL6tqExlItI1VlgcZ5LYJSvZrsQcDPumRcyC7A0KsEiqn/RGA1cD1Xf3IAWw+zzJb7aYnSWkVI0jIlVWI3hWTFn4d7LQkInSH50SelLIdZq88yKjrr9x5qJsZOXqLMw+OlyhVCeaI6/69Z35wfgTKwxPGpttnJIP3gxHcaiuFMg=="
content2 := "T5r3jhxWi5WM78SMzgA4tDgVIi2kKs6nyNd3JCHWRnTYr4mvZE+JYBQkoBwVC2mLqorJCzU/2OagNkcbmShbZ4MDnonvZY06K4hIMQjqStnQV5QDDnkuXfVShB9BLIzPQFICE1dnutqWiWOtZ9rHRbmYrtFTD9fxI5B8zCM9hLoEZ5b1Y/RkpMJOL5WXNSsHqeas6BwQjDbhSptwdjCj08CyznfhPluYryrl/3gICKA/KsiA9QZI4LgcL2y1uelAYtjjAwG2hfK/mMtcFfOyipYYqLIfuDjEhGwP8Ng/+y5drjcKJLJa8r+YrI9zi9y5pohPccn6swSO2xxF9hXp8w=="

// Hello, World
println( rsaD( content1, privateKey ) )

// Bullet
println( rsaD( content2, privateKey ) )
```