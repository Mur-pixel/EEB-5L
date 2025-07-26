interface Env {
    mode: string | undefined;
    api: {
        GITHUB_AUTHENTICATION_URL: string | undefined;
    };
}

const env: Env = {
    mode: process.env.NODE_ENV,
    api: {
        GITHUB_AUTHENTICATION_URL: process.env.REACT_APP_GITHUB_AUTHENTICATION_URL,
    },
};

export default env;
