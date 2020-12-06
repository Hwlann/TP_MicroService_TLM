# TP_MicroService_TLM

# Creators

* MALHERBE Timoté
* LORTET César
* TOURNATORY Loïc
                
# Architecture
![archi_micro_service](https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fliquipedia.net%2Fcommons%2Fimages%2F6%2F63%2FWIP-logo.png&f=1&nofb=1)

# Routes
## Author-Service:
#### GET
#### POST
#### PUT
#### DELETE

## Comment-Service: 
#### GET
- /comments
- /comments/{id}
- /comments/{author} ***WIP***
- /comments/{quote}  ***WIP***
#### POST
- /comments/new
{ String Author = "Anonymous", String Content }
- /comments/
{ Comment comment }
#### PUT
- /comments/{id}/edit
{ String Content }
#### DELETE
- /comments/{id}

## Quote-Service:
#### GET
- /quotes
- /quotes/{id}
- /quotes/{author} ***WIP***
#### POST
- /quotes/new
{ String Content }
- /quotes/
{ Quote quote }
- /quotes/{id}/add-comment/
{ int comment_id }
#### PUT
- /quotes/{id}/edit
{ String Content }
#### DELETE
- /quotes/{id}
