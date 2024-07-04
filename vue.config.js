const { defineConfig } = require('@vue/cli-service')
const TerserPlugin = require("terser-webpack-plugin");
module.exports = defineConfig({
  devServer: {
    open: true,
    hot: true,
    host: 'localhost',
    port: 8081,
    proxy: {
      '/api': {
        target: 'http://110.41.80.141:8888',
        // target: 'http://localhost:8888',
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    },
    client: {
      overlay: false,
    },
  },
  transpileDependencies: true,
  configureWebpack: {
    optimization: {
      minimizer: [
        new TerserPlugin({
          terserOptions: {
            compress: {
              drop_console: true,
            }
          }
        })
      ]
    },
  }
})
