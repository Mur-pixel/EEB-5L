import React from "react"

const ConditionalRenderTest = () => {
    const isLoggedIn = false

    return (
        <div>
            {
                isLoggedIn ?
                    <h1>환영합니다</h1> :
                    <h1>로그인 이후 서비스를 이용할 수 있습니다.</h1>
            }
        </div>
    )
}

export default ConditionalRenderTest