export const ControlFlowSwitch = () => {
    let grade = Math.floor(Math.random() * 10) + 1
    let gradeName

    switch (grade) {
        case 1: gradeName = "1등급"; break;
        case 2: gradeName = "2등급"; break;
        case 3: gradeName = "3등급"; break;
        case 4: gradeName = "4등급"; break;
        case 5: gradeName = "5등급"; break;
        case 6: gradeName = "6등급"; break;
        case 7: gradeName = "7등급"; break;
        case 8: gradeName = "8등급"; break;
        case 9: gradeName = "9등급"; break;

        default: gradeName = "규격 외";
    }

    return (
        <div className="bg-gray-100 p-5 my-4 rounded-md border-2 border-blue-500 font-bold text-left">
            <h3>Javascript switch</h3>
            <p>랜덤 값: {grade}</p>
            <p>등급: {gradeName}</p>
            <pre>
                {`
                    switch (grade) {
                        case 1: gradeName = "1등급"; break;
                        case 2: gradeName = "2등급"; break;
                        case 3: gradeName = "3등급"; break;
                        case 4: gradeName = "4등급"; break;
                        case 5: gradeName = "5등급"; break;
                        case 6: gradeName = "6등급"; break;
                        case 7: gradeName = "7등급"; break;
                        case 8: gradeName = "8등급"; break;
                        case 9: gradeName = "9등급"; break;
                
                        default: gradeName = "규격 외";
                    }
                `}
            </pre>
        </div>
    )
}