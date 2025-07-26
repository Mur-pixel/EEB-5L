interface Env {
    mode: string | undefined;
    api: {
        KAKAO_AUTHENTICATION_URL: string | undefined;
    };
    origin: string | undefined;
}

const env: Env = {
    mode: process.env.NODE_ENV,
    api: {
        KAKAO_AUTHENTICATION_URL: process.env.REACT_APP_KAKAO_AUTHENTICATION_URL,
    },
    origin: process.env.DATA_ORIGIN,
};

export default env;