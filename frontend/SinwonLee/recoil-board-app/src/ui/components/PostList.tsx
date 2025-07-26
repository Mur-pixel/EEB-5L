// components/PostList.tsx
import React, { useEffect } from 'react';
import { useRecoilState, useSetRecoilState, useRecoilValueLoadable } from 'recoil';
import { postsState, postsLoadingState, fetchPostsSelector } from '../../atoms/postState';

function PostList() {
    const [posts, setPosts] = useRecoilState(postsState);
    const [loading, setLoading] = useRecoilState(postsLoadingState);
    const postsLoadable = useRecoilValueLoadable(fetchPostsSelector);

    useEffect(() => {
        if (postsLoadable.state === 'hasValue') {
            setPosts(postsLoadable.contents);
            setLoading(false);
        } else if (postsLoadable.state === 'loading') {
            setLoading(true);
        } else if (postsLoadable.state === 'hasError') {
            console.error('불러오기 오류:', postsLoadable.contents);
            setLoading(false);
        }
    }, [postsLoadable.state]);

    if (loading) {
        return (
            <div className="flex justify-center items-center min-h-screen text-xl font-semibold">
                로딩 중...
            </div>
        );
    }

    return (
        <div className="p-6 bg-gray-100 min-h-screen">
            <h2 className="text-2xl font-bold mb-4 text-gray-800">📚 게시글 목록</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {posts.slice(0, 9).map((post) => (
                    <div
                        key={post.id}
                        className="bg-white p-4 rounded-xl shadow-md hover:shadow-lg transition-shadow"
                    >
                        <h3 className="text-lg font-semibold text-blue-600 mb-2">{post.title}</h3>
                        <p className="text-gray-700 text-sm">{post.body}</p>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default PostList;
