module.exports = {
  devServer: {
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Method': 'POST,GET'
    },
    proxy: {
      '/api': {
        target: 'http://10.0.0.88:8080',
      }
    }
  },
  configureWebpack: {
    devtool: 'source-map'
  }
}
