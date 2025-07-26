import React, {useEffect, useState} from "react"

const PostList = () => {
    const [postList, setPostList] = useState([])
    const [loading, setLoading] = useState(true)

    const fetchPostList = async () => {
        try {
            const response = await fetch('https://jsonplaceholder.typicode.com/posts')
            const data = await response.json()
            setPostList(data)
        } catch (error) {
            console.error('데이터 불러오기 실패:', error)
        } finally {
            setLoading(false)
        }
    }

    // React 컴포넌트가 구동 될 때 한 번만 실행됨
    useEffect(() => {
        fetchPostList()
    }, []);

    if (loading) {
        return (
            <div className="flex justify-center items-center min-h-screen text-xl font-semibold">
                로딩중 ......
            </div>
        )
    }

    return (
        <div className="p-6 bg-gray-100 min-h-screen">
            <h2 className="text-2xl font-bold mb-4 text-gray-800">
                게시글 목록
            </h2>
            <div>
                { postList.slice(0, 9).map((postList) => (
                    <div
                        key={ postList.id }
                        className="bg-white p-4 rounded-xl
                                    shadow-md hover:shadow-lg transition-shadow"
                    >
                        <h3 className="text-lg font-semibold text-blue-600 mb-2">
                            { postList.title }
                        </h3>
                        <p className="text-gray-700 text-sm">{ postList.body }</p>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default PostList