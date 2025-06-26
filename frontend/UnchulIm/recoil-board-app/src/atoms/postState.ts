import { atom, selector } from 'recoil';

export const postsState = atom({
    key: 'postsState',
    default: [],
});

export const postsLoadingState = atom({
    key: 'postsLoadingState',
    default: true,
});

export const fetchPostsSelector = selector({
    key: 'fetchPostsSelector',
    get: async ({ get }) => {
        try {
            const response = await fetch('https://jsonplaceholder.typicode.com/posts');
            return await response.json();
        } catch (err) {
            console.error('API 오류:', err);
            return [];
        }
    },
});
