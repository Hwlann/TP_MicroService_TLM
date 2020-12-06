# TP_MicroService_TLM

# Creators

* MALHERBE Timoté
* LORTET César
* TOURNATORY Loïc
                
# Architecture
![archi_micro_service](https://your-copied-image-address)

# Root


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
- /comments/{quote} ***WIP***
#### POST
- /comments/new
{ String Author = "Anonymous", String Content }
- /comments/
{ Comment comment }
#### PUT
- /comment/{id}/edit
{ String Content }
#### DELETE
- /comment/{id}

## Quote-Service:
#### GET
#### POST
#### PUT
#### DELETE

