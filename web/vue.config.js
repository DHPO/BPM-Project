const webpack = require('webpack')

module.exports = {
  devServer: {
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Method': 'POST,GET',
      "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept"
    },
    proxy: {
      '/api': {
        // target: 'http://10.0.0.92:8080',
        // target: 'http://10.162.72.24:8080'
        target: 'http://localhost:8081'
      }
    }
  },
  configureWebpack: {
    devtool: 'source-map',
    module: {
      rules: [
        {
          test: /\.js$/,
          exclude: /node_modules(?!\/quill-image-drop-module|quill-image-resize-module)/,
          loader: 'babel-loader'
        }
      ]
    },
    plugins: [
      new webpack.ProvidePlugin({
        'window.Quill': 'quill'
      })
    ]
  }
}
