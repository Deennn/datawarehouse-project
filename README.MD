
# ProgressSoft Assignment


## Task description


A Data warehouse for analyzing Bloomberg FX deals and persist them into Database

## Requirements
To run this application, you will need the following: 

- Docker
- Clone the project Repository
- Install Make


## To run Locally via Docker or Make

Clone the project

```bash
  git clone https://github.com/Deennn/datawarehouse-project
```

Kindly go to the project directory

```bash
  cd Datawarehouse
```

Run Via Make

```bash
  make run
```

Run Via Docker
```bash
  - docker compose build
  - docker compose up 
```

```http
  POST /api/v1/fx-deals/save
```

```bash

curl --location 'http://localhost:8080/api/v1/fx-deal/save' \
--header 'Content-Type: application/json' \
--data '{
  "deal_unique_id": "yttr-8954fg",
  "from_currency_code": "NGN",
  "ordering_currency": "USD",
  "to_currency_code": "USD",
  "deal_amount_in_ordering_currency": 53.79,
  "deal_timestamp": "2022-01-08 12:30:45"
}'

```
#### Request payload
```
{
  "deal_unique_id": "qwudsj-123",
  "from_currency_code": "NGN",
  "ordering_currency": "USD",
  "to_currency_code": "USD",
  "deal_amount_in_ordering_currency": 53.79,
  "deal_timestamp": "2022-01-08T12:30:45"
}
```
#### Response
```
{
    "message": "FXDeal save successfully",
    "success": true,
    "payload": {
        "dealUniqueId": "qwudsj-123",
        "fromCurrencyCode": "NGN",
        "orderingCurrency": "USD",
        "toCurrencyCode": "USD",
        "dealAmountInOrderingCurrency": 53.79,
        "dealTimestamp": "2022-01-08T12:30:45"
    }
}

```
#### Get FX Deal

```http
  GET /api/v1/fx-deals/${id}
```

```bash
curl --location 'http://localhost:8080/api/v1/fx-deal/yttr-8954fg'

#### Request payload
``` 
localhost:8080/api/v1/fx-deal/yttr-8954fg
```
#### Response
```
{
    "message": "success",
    "success": true,
    "payload": {
        "dealUniqueId": "yttr-8954fg",
        "fromCurrencyCode": "NGN",
        "orderingCurrency": "USD",
        "toCurrencyCode": "USD",
        "dealAmountInOrderingCurrency": 53.79,
        "dealTimestamp": "2022-01-08T12:30:45"
    }
}

```http
  GET /api/v1/fx-deals
```

### Get all FX Deals

```bash
curl --location 'http://localhost:8080/api/v1/fx-deals?pageNo=0&pageSize=10&sortBy=id&sortDir=asc'
```

#### Request
``` 
localhost:8080/api/v1/fx-deals
```

### Response
```
{
    "message": "success",
    "success": true,
    "payload": {
        "content": [
            {
                "dealUniqueId": "yttr-8954fg",
                "fromCurrencyCode": "NGN",
                "orderingCurrency": "USD",
                "toCurrencyCode": "USD",
                "dealAmountInOrderingCurrency": 53.79,
                "dealTimestamp": "2022-01-08T12:30:45"
            }
        ],
        "pageNo": 1,
        "pageSize": 10,
        "totalElements": 1,
        "totalPages": 1,
        "last": true
    }
}
```