# Sistema de Consulta de Sócios

Este projeto é uma aplicação web para listar e detalhar sócios de empresas, utilizando **React** no front-end e **Spring Boot** no back-end. Ele integra dados de APIs externas e oferece filtro por participação societária.

<img width="960" height="1518" alt="image" src="https://github.com/user-attachments/assets/7db2cc12-c61e-4881-a01f-27c26ba64039" />

---

## Funcionalidades

- **Listagem de sócios**: Exibe todos os sócios inicialmente.  
- **Filtro por participação**: Permite filtrar sócios por participação mínima (%) usando um campo de input.  
- **Detalhes do sócio**: Mostra informações detalhadas do sócio, incluindo:
  - Nome, CNPJ e percentual de participação.
  - Dados da Receita Federal (via API `publica.cnpj.ws`).
  - Mapa do CEP do sócio (Google Maps Embed).  

---

## Back-end (Spring Boot)

- **Endpoints**:

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/socios` | Lista todos os sócios. Aceita parâmetro opcional `participacaoMin` (%) |
| GET | `/socios/cnpj/{cnpj}` | Retorna detalhes do sócio pelo CNPJ |

- **Service**:
  - Consulta dados da API externa de sócios e Receita Federal.
  - Gera URL de mapa para o CEP do sócio.
- **Controller**:
  - Mapeia endpoints e formata a resposta no padrão necessário pelo front-end.

---

## Exemplos de chamadas à API

### 1. Listar todos os sócios

**Endpoint:** `GET /socios`  

**Com curl:**

```bash
# Todos os sócios
curl -X GET http://localhost:8081/socios/

# Sócios com participação mínima de 10%
curl -X GET "http://localhost:8081/socios?participacaoMin=10"
```

**Exemplo de resposta:**

```json
[
  {
    "nome": "ROBERT BOSCH LIMITADA",
    "participacao": 28,
    "cnpj": "45.990.181/0001-89",
    "cep": "20095555",
    "urlMapa": "https://www.google.com/maps/search/?api=1&query=20095555",
    "cnaesSecundarios": [
      "6110-8/03 - SERVICOS DE COMUNICACAO MULTIMIDIA - SCM",
      "6190-6/99 - OUTRAS ATIVIDADES DE TELECOMUNICACOES NAO ESPECIFICADAS ANTERIORMENTE"
    ]
  },
  {
    "nome": "IBM BRASIL - INDUSTRIA MAQUINA E SERVIÇO LTDA",
    "participacao": 20,
    "cnpj": "333.722.51/0062-78",
    "cep": "20095555",
    "urlMapa": "https://www.google.com/maps/search/?api=1&query=20095555",
    "cnaesSecundarios": [
      "6110-8/03 - SERVICOS DE COMUNICACAO MULTIMIDIA - SCM",
      "6190-6/99 - OUTRAS ATIVIDADES DE TELECOMUNICACOES NAO ESPECIFICADAS ANTERIORMENTE"
    ]
  }
]
```

---

### 2. Detalhar sócios de uma empresa pelo CNPJ

**Endpoint:** `GET /socios/cnpj/{cnpj}`  

**Com curl:**

```bash
# Substitua {cnpj} pelo CNPJ da empresa
curl -X GET http://localhost:8081/socios/cnpj/45990181000189
```

**Exemplo de resposta:**

```json
[
  {
    "nome": "ANDRE AUGUSTO GARCEZ BERTOLIN",
    "cnpj": "45990181000189"
  },
  {
    "nome": "EDUARDO GASTON DIAZ PEREZ",
    "cnpj": "45990181000189"
  }
]
```

---

### Observações de teste

- É possível testar diretamente no navegador ou com ferramentas como **Postman**.  
- O endpoint `/socios` suporta query parameters (`participacaoMin`) e retorna a lista filtrada.  
- O endpoint `/socios/cnpj/{cnpj}` retorna somente os nomes dos sócios da empresa consultada.  
- Em caso de erro ou timeout da API externa, a resposta conterá mensagem de erro apropriada:

```json
{
  "erro": "A requisição para a API demorou demais e foi encerrada."
}
```

---

## Front-end (React)

- **Componentes principais**:
  - `App.js` → Gerencia estado global e lógica de filtros.
  - `SocioTable.js` → Tabela de listagem de sócios com botão de detalhes.
  - `SocioDetail.js` → Mostra informações detalhadas do sócio.  
- **Filtros e ações**:
  - Input numérico com label "Participação mínima (%)" e placeholder "digitar".
  - Botão "Filtrar" para aplicar o filtro.
  - Botões de ação em preto.

---

## Como rodar

1. **Back-end**:
   ```bash
   ./mvnw spring-boot:run
   ```
   - A aplicação roda em `http://localhost:8081`.

2. **Front-end**:
   ```bash
   npm install
   npm start
   ```
   - A aplicação roda em `http://localhost:3000`.

---

## Tecnologias

- **Back-end**: Java, Spring Boot, WebClient, REST API.  
- **Front-end**: React, Hooks, CSS inline.  
- **APIs externas**: WireMock (dados de sócios), `publica.cnpj.ws`, Google Maps Embed.
