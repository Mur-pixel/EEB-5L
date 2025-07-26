export const mfConfig = {
  name: "vuetifyTailwindBoardApp",
  filename: "remoteEntry.js",
  exposes: {
    './vuetifyBoardBootstrap': './src/bootstrap',
    // './vuetifyBoardModuleStore': './src/store/board/boardModule',
    // './boardRegisterBootstrap': './src/bootstrapper/boardRegisterBootstrap',
    // './boardReadBootstrap': './src/bootstrapper/boardReadBootstrap',
    // './boardModifyBootstrap': './src/bootstrapper/boardModifyBootstrap',
    // './boardListBootstrap': './src/bootstrapper/boardListBootstrap',
  },
  shared: {
    vue: { singleton: true, requiredVersion: "^3.2.19" },
    vuetify: { singleton: true, requiredVersion: "^3.8.8" },
  },
  dts: false
};
