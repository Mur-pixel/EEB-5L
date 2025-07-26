export const mfConfig = {
  name: "testGameApp",
  filename: "remoteEntry.js",
  exposes: {
    "./App": "./src/App.svelte",
  },
  shared: ["svelte"],
  dts: false,
};
