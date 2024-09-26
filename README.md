# kotlin Problem Detail Exceptions Lib

[![Versão](https://img.shields.io/badge/vers%C3%A3o-1.2.1-beta)](https://github.com/seu-usuario/sua-lib/releases)
[![Licença](https://img.shields.io/badge/licen%C3%A7a-MIT-green)](https://opensource.org/licenses/MIT)

Esse projeto tem por objetivo prover uma biblioteca kotlin (spting framework) que permite implementar de maneira simples e rápida um padrão de exceptions, seguindo os conceitos da [RFC de problem details](https://datatracker.ietf.org/doc/html/rfc7807)

## Instalação

``EM DOCUMENTAÇÃO``
...

## Criando Exceptions Customizadas

Para usar o pacote recomendamos que sejam criadas excessões extendendo da classe `io.carlosrmfernandes.kotlinproblemdetailexceptions.exceptions` conforme a classe de exemplo abaixo:

```php

use io.carlosrmfernandes.kotlinproblemdetailexceptions.exceptions;

class ClientException( ) : ProblemDetailException( )
```

Em seguida devemos criar dentro do construtor uma chamada para o construtor da classe Pai passando os parâmetros necessários para a construção da exception.

```php

(
  title:        'Titulo curto para erro. Deve ser imutável',
  detail:       'Descrição mais detalhada do erro podendo conter variaveis dinâmicas.' .
  userTitle:    'Titulo amigavel para usuário final que pode ver o erro',
  userMessage:  'Detalhamento amigavel para usuário que pode ver o erro',
  httpStatus:    500,
  internalCode: 'SHRD0001',
  cause = cause
);

```

O resultado final deve ser algo como o exemplo abaixo

```
<?php

class ExampleException(
    httpStatus: HttpStatus? = null,
    cause: Throwable? = null,
) : ProblemDetailException(
    title:        'Titulo curto para erro. Deve ser imutável',
    detail:       'Descrição mais detalhada do erro podendo conter variaveis dinâmicas.' .
    userTitle:    'Titulo amigavel para usuário final que pode ver o erro',
    userMessage:  'Detalhamento amigavel para usuário que pode ver o erro',
    httpStatus:    500,
    internalCode: 'SHRD0001',
    cause = cause
)

```

Desta maneira é possível ter uma exception bem legível, completa e com uma forma de invocação simples e direta


```php
try {
    
} catch (exception: Exception) {
    throw ExampleException(exception)
}

```

### Log por Exception

Tambem é possível configurar o log em cada exception usando a opção `$logThrow`. Essa opção permite que possamos
configurar cada excessão para gerar logs, ou não, independente da configuração geral. Caso ela seja omitida a
configuração geral de logs será levada em consideração.

Ex:

```php

class ExampleException(
    cause: Throwable? = null,
    val logThrow: Boolean = true
) : ProblemDetailException(
    title:        'Titulo curto para erro. Deve ser imutável',
    detail:       'Descrição mais detalhada do erro podendo conter variaveis dinâmicas.' .
    userTitle:    'Titulo amigavel para usuário final que pode ver o erro',
    userMessage:  'Detalhamento amigavel para usuário que pode ver o erro',
    httpStatus:    500,
    internalCode: 'SHRD0001',
    cause = cause
)   
```
## Adicionando Exception no GlobalExceptionHandler

```php
@ExceptionHandler(
  UnexpectedException::class,
  ServerException::class,
  ClientException::class
)
fun handleProblemDetailException(
  ex: ProblemDetailException,
): ResponseEntity<Map<String, Any?>> {
  val body = ex.toMap()
  return ResponseEntity.status(ex.httpStatus).body(body)
} 
```
Saida da exception:

```php
{
    "type": "UnexpectedException",
    "title": "Erro",
    "status": 500,
    "detail": "{\"exception_code\":1942407959,\"exception_line\":15,\"exception_message\":\"Erro\",\"exception_file\":\"ExampleController.kt\"}",
    "internal_code": "UNEXPECTED_ERROR",
    "message": "Erro",
    "user_message": "Entre em contato com o administrador do sistema",
    "user_title": "Ocorreu um erro inesperado",
    "location": "UnexpectedException:40",
    "trace_id": "trace_id_value",
    "previous_message": "Erro",
    "previous_type": "Exception",
    "previous_code": 1942407959,
    "previous_location": "ExampleController.kt:15"
} 
```

## Contribuindo

O projeto esta em fase de construção e apontamentos de melhorias são muito importantes para o
crescimento. Para sugerir uma melhoria use as Issues do github.

## Licença

[MIT](https://choosealicense.com/licenses/mit/)
