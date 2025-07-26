import React from "react";
import ReactDOM from "react-dom/client";

import "./index.css";
import {Container, Typography} from "@mui/material";
import {CalculationExample} from "./math_operation/CalculationExample.tsx";
import {VariableAndDataTypes} from "./data_type/VariableAndDataTypes.tsx";
import {LogicalOperationExample} from "./logical_operation/LogicalOperationExample.tsx";
import {TypeCoercion} from "./type_convert/TypeCoercion.tsx";
import {ControlFlowIf} from "./control_flow/ControlFlowIf.tsx";
import {ControlFlowSwitch} from "./control_flow/ControlFlowSwitch.tsx";
import {ControlFlowFor} from "./control_flow/ControlFlowFor.tsx";
import {ControlFlowForEach} from "./control_flow/ControlFlowForEach.tsx";
import {MapExample} from "./map/MapExample.tsx";
import {FilterExample} from "./filter/FilterExample.tsx";
import {StringExample} from "./string_test/StringExample.tsx";
import {RegularExpression} from "./regular_expression/RegularExpression.tsx";
import {ArrayPushExample} from "./array/store/ArrayPushExample.tsx";
import {ArrayPopExample} from "./array/store/ArrayPopExample.tsx";
import {ArrayShiftExample} from "./array/store/ArrayShiftExample.tsx";
import {ArrayUnShiftExample} from "./array/store/ArrayUnShiftExample.tsx";
import {ArrayReduceExample} from "./array/ArrayReduceExample.tsx";
import {ArrayFindExample} from "./array/search/ArrayFindExample.tsx";
import {FirstQuiz} from "./quiz/FirstQuiz.tsx";
import {EmergencyQuiz} from "./quiz/EmergencyQuiz.tsx";
import {SecondQuiz} from "./quiz/SecondQuiz.tsx";
import {ThirdQuiz} from "./quiz/ThirdQuiz.tsx";
import {ArrayIncludesExample} from "./array/search/ArrayIncludesExample.tsx";
import {ArrayIndexOfExample} from "./array/search/ArrayIndexOfExample.tsx";
import {ArraySliceExample} from "./array/ArraySliceExample.tsx";
import {ArraySpliceExample} from "./array/ArraySpliceExample.tsx";
import {ArraySortExample} from "./array/sort/ArraySortExample.tsx";
import {ArrayReverseExample} from "./array/sort/ArrayReverseExample.tsx";
import {ArrayEveryExample} from "./array/condition/ArrayEveryExample.tsx";
import {ArraySomeExample} from "./array/condition/ArraySomeExample.tsx";
import {ArrayFlatExample} from "./array/ArrayFlatExample.tsx";
import {FirstProblemVersionOne} from "./quiz2/FirstProblemVersionOne.tsx";
import {FirstProblemVersionTwo} from "./quiz2/FirstProblemVersionTwo.tsx";
import {FirstProblemVersionThree} from "./quiz2/FirstProblemVersionThree.tsx";
import {SecondProblemVersionOne} from "./quiz2/SecondProblemVersionOne.tsx";
import {SecondProblemVersionTwo} from "./quiz2/SecondProblemVersionTwo.tsx";
import {SecondProblemVersionThree} from "./quiz2/SecondProblemVersionThree.tsx";
import {ThirdProblem} from "./quiz2/ThirdProblem.tsx";

const App = () => {
    return (
        <Container maxWidth="md" sx={{ padding: "32px 16px", textAlign: "left" }}>
            <Typography variant="h4" gutterBottom sx={{ textAlign: "center" }}>
                JavaScript 필수 요소를 파악해보자!
            </Typography>
            <CalculationExample/>
            <VariableAndDataTypes/>
            <LogicalOperationExample/>
            <TypeCoercion/>
            <ControlFlowIf/>
            <ControlFlowSwitch/>
            <ControlFlowFor/>
            <ControlFlowForEach/>
            <MapExample/>
            <FilterExample/>
            <StringExample/>
            <RegularExpression/>
            <ArrayPushExample/>
            <ArrayPopExample/>
            <ArrayShiftExample/>
            <ArrayUnShiftExample/>
            <ArrayReduceExample/>
            <ArrayFindExample/>
            <FirstQuiz/>
            <EmergencyQuiz/>
            <SecondQuiz/>
            <ThirdQuiz/>
            <ArrayIncludesExample/>
            <ArrayIndexOfExample/>
            <ArraySliceExample/>
            <ArraySpliceExample/>
            <ArraySortExample/>
            <ArrayReverseExample/>
            <ArrayEveryExample/>
            <ArraySomeExample/>
            <ArrayFlatExample/>
            <FirstProblemVersionOne/>
            <FirstProblemVersionTwo/>
            <FirstProblemVersionThree/>
            <SecondProblemVersionOne/>
            <SecondProblemVersionTwo/>
            <SecondProblemVersionThree/>
            <ThirdProblem/>
        </Container>
    )
}

export default App;