const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  publicPath:'./',
  transpileDependencies: true,
  chainWebpack: config => {
    config.plugin('define').tap(definitions => {
        Object.assign(definitions[0]['process.env'], {
          NODE_HOST: '"http://127.0.0.1:8888/"',
        });
        return definitions;
    });
  },
  pluginOptions: {
    electronBuilder: {
      chainWebpackMainProcess: (config) => {
        config.output.filename((file) => {
            if (file.chunk.name === 'index') {
                return 'background.js';
            } else {
                return '[name].js';
            }
          });
        }
      }
    }
})
