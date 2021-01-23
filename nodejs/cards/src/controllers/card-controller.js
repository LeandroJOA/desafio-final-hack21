const { request, response } = require('express')
const neDB = require('../configurations/database')
const api = {}

api.findAll = (request, response) => {

    neDB.find({}).exec(function(exception, cards) {

        if (exception) {
            const sentence = "ERROR ao listar todos os registros de 'cards'!"
            console.log(sentence, exception)

            response.status(exception.status)
            response.json({ 'message': sentence })
        }

        response.status(200)
        response.json(cards)
    })
}

api.findById = (request, response) => {

    const id = request.params.id

    neDB.findOne({ _id: id }).exec(function(exception, card) {

        if (exception) {
            const sentence = "ERROR ao listar o registro de 'card' requisitado!"
            console.log(sentence, exception)

            response.status(exception.status)
            response.json({ 'message': sentence })
        }

        response.status(200)
        response.json(card)
    })
}

// Consumo desta API:
// localhost:3000/cards/paginationAndSorting?pageSize=[TamanhoPagina]&itemsSkip=[RegistrosPular]&sortBy=[CampoOrdenação]
api.findSorted = (request, response) => {

    let sortBy = request.query.sortBy
    let pageSize = request.query.pageSize
    let itemsSkip = request.query.itemsSkip

    // Definição de valores padrão
    if (typeof sortBy === 'undefined') {
        sortBy = "cardNumber"
    }
    if (typeof pageSize === 'undefined') {
        pageSize = 10
    }
    if (typeof itemsSkip === 'undefined') {
        itemsSkip = 0
    }
    

    neDB.find({}).sort({ [sortBy]: 1 }).skip(itemsSkip).limit(pageSize).exec(function (exception, cards) {
        if (exception) {
            const sentence = "ERROR ao listar todos os registros de 'cards' de forma ordenada!"
            console.log(sentence, exception)

            response.status(exception.status)
            response.json({ 'message': sentence })
        }

        response.status(200)
        response.json(cards)
    });
}

api.save = (request, response) => {

    const canonical = request.body

    neDB.insert(canonical, function(exception, card) {

        if (exception) {
            const sentence = "ERROR ao inserir um novo registro de 'card'!"
            console.log(sentence, exception)

            response.status(exception.status)
            response.json({'message': sentence})
        }

        response.status(201)
        response.json(card)
    })
}

api.deleteById = (request, response) => {
    
    const id = request.params.id

    neDB.remove({ _id: id }, {}, function(exception, card) {
        if (exception) {
            const sentence = "ERROR ao deletar registro de 'card' solicitado!"
            console.log(sentence, exception)

            response.status(exception.status)
            response.json({ 'message': sentence})
        }

        response.status(200)
        response.json(card)
    })
}

api.updateById = (request, response) => {
    
    const id = request.params.id
    const canonical = request.body

    neDB.update({ _id: id }, { 
        cardNumber: canonical.cardNumber,
        embossName: canonical.embossName,
        customerName: canonical.customerName,
        documentNumber: canonical.documentNumber,
        motherName: canonical.motherName,
        address: canonical.address,
        city: canonical.city
    }, {}, function (exception, card) {
            if (exception) {
                const sentence = "ERROR ao deletar registro de 'card' solicitado!"
                console.log(sentence, exception)

                response.status(exception.status)
                response.json({ 'message': sentence })
            }

            response.status(200)
            response.json(card)
    })
}

module.exports = api