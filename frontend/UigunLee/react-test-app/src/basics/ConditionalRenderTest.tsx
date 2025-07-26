import React from "react";

// React 에서 컴포넌트 만들기
// 1. 일단 그냥 const를 작성합니다.
// 2. 목적에 따라 작성할 이름을 작성합니다.
// 3. = () => {} 를 작성합니다.
// 4. Optional(선택적) 하게 () 소괄호 내에 뭔가 작성될 수 있음 (파라미터, 인자)

const ConditionalRenderTest = () => {
    const isLoggedIn = false

    return (
        <div>
            {
                isLoggedIn ?
                    <h1>환영합니다</h1> :
                    <h1>로그인 이후 서비스를 이용할 수 없습니다.</h1>
            }
        </div>
    )
}

// export default SimpleCounter는
// 외부에서 SimpleCounter를 볼 수 있도록 만들었음을 의미
// 다른 소스 코드에서 import 등으로 재사용 할 수 있게 만들겠다는 뜻

export default ConditionalRenderTest