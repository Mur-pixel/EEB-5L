export const FirstProblemVersionTwo = () => {
    const cartInfo = [
        { id: 1, name: "사과", price: 1500, quantity: 4 },
        { id: 2, name: "바나나", price: 1000, quantity: 6 },
        { id: 3, name: "오렌지", price: 2000, quantity: 3 },
        { id: 4, name: "수박", price: 10000, quantity: 1 },
        { id: 5, name: "망고", price: 3500, quantity: 2 }
    ];

    let totalPrice = 0
    cartInfo.forEach((cartItem) => {
        totalPrice += cartItem.price * cartItem.quantity
    })

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>Quiz2-1번 문제 버전 2</h3>
            <p>전체 가격: {totalPrice}</p>
        </div>
    )
}