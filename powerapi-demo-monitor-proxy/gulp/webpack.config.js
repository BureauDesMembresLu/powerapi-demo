const path = require('path');

const config = require('./config');

const rootDirectory = path.resolve('.');

module.exports = {
    entry: {app: path.join(rootDirectory, config.app + 'js/app')},
    output: {path: path.join(rootDirectory, config.dist + 'js')},
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: ['babel-loader']
            }
        ]
    }
};
