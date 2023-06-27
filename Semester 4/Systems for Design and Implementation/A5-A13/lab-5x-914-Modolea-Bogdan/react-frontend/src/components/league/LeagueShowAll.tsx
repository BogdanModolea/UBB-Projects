import { useEffect, useState } from "react";
import { League } from "../../models/League";
import { BACKEND_API_URL } from "../../constants";


function LeagueShowAll() {
    const [leagues, setLeagues] = useState([]);


    useEffect(() => {
        fetch(`${BACKEND_API_URL}leagues`)
            .then(response => response.json())
            .then(data => setLeagues(data));
    }, []);

    return (
      <div className="App">
        <h1>League List</h1>
        <table>
            <tr>
                <th>#</th>
                <th>League Name</th>
                <th>Region</th>
                <th>Year</th>
                <th>Best Player</th>
                <th>Audience</th>
            </tr>
            {leagues.map((league: League, index) => (
                <tr>
                    <td>{index}</td>
                    <td>{league.abbreviation}</td>
                    <td>{league.region}</td>
                    <td>{league.year}</td>
                    <td>{league.bestPlayer}</td>
                    <td>{league.audience}</td>
                </tr>
            ))}
        </table>
      </div>
    )
  }
  
  export default LeagueShowAll;
  