const api = require('../controllers/card-controller')

module.exports = (app) => {
    app.route('/cards/paginationAndSorting')
        .get(api.findSorted),
    app.route('/cards')
        .get(api.findAll)
        .post(api.save),
    app.route('/cards/:id')
        .get(api.findById)
        .delete(api.deleteById)
        .put(api.updateById)

}