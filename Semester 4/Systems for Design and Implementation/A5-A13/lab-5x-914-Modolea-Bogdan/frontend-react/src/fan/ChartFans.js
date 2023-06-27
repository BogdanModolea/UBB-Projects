import React, { useEffect, useState } from 'react'
import CanvasJSReact from '@canvasjs/react-charts';
import axios from 'axios';
import * as myConstClass from '../constants/constants';

var CanvasJS = CanvasJSReact.CanvasJS;
var CanvasJSChart = CanvasJSReact.CanvasJSChart;
export default function ChartFans() {
    const serverLink = myConstClass.SERVER_LINK + `/fans`;

    const[fans, setFans] = useState([]);
    const[currentData, setData] = useState([]);
    const[futureData, setFutureData] = useState([]);

    useEffect(() => {
        loadPredictFans();
    }, []);

    const loadPredictFans = async () => {
        const result = await axios.get(serverLink + `/predict-fans`);
        const newFans = await axios.get(serverLink + `/get-fans-counter`);
        setFans(newFans.data);

        console.log(result.data);

        generateData(newFans.data);
        generateFutureData(newFans.data, result.data);
    }

    const generateData = (fans) => {
        const data = [];
        fans.map((fan) => { 
            data.push({x: fan.year, y: fan.counter});
        })
        setData(data);
    }

    const generateFutureData = (fans, result) => {
        const data = [];
        let last = -1;
        fans.map((fan) => {
            last = fan.counter;
        })
        data.push({x: 2023, y: last});
        data.push({x: 2024, y: result[0]});
        data.push({x: 2025, y: result[1]});
        data.push({x: 2026, y: result[2]});
        setFutureData(data);
    }


    const options = {
        animationEnabled: true,	
        title:{
            text: "Estimated Number of Fans"
        },
        axisY : {
            title: "Number of Fans",
        },
        toolTip: {
            shared: true
        },
        data: [{
            type: "line",
            name: "Expected Data",
            showInLegend: true,
            dataPoints: futureData
        },
        {
            type: "line",
            name: "Current Data",
            showInLegend: true,
            dataPoints: currentData
        }]
    }

    return (
        <div>
            <CanvasJSChart options = {options}
				/* onRef={ref => this.chart = ref} */
			/>
        </div>
    )
}

