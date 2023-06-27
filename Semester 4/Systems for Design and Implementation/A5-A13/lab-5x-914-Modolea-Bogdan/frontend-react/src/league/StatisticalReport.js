import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import * as myConstClass from '../constants/constants';
import Loading from '../constants/Loading';

export default function StatisticalReport() {

    const[leagues, setLeagues] = useState([]);
    const[loading, setLoading] = useState(true);

    const[currentPage, setCurrentPage] = useState(1);
    const[npage, setNPages] = useState(0);
    const[numbers1, setNumbers1] = useState([0, 1, 2, 3].slice(1));
    const[numbers2, setNumbers2] = useState([]);
    const recordsPerPage = 100;


    useEffect(() => {
        loadLeagues();
    }, []);

    const loadRaportWithPage=async(page)=>{
        if(page === 1){
            setNumbers1([0, 1, 2, 3].slice(1));
            if(npage !== 0)
                setNumbers2([npage - 3, npage - 2, npage - 1, npage].slice(1));
        }else if(page === 2){
            setNumbers1([0, 1, 2, 3, 4].slice(1));
            if(npage !== 0)
                setNumbers2([npage - 3, npage - 2, npage - 1, npage].slice(1));
        }else if(page === 3){
            setNumbers1([0, 1, 2, 3, 4, 5].slice(1));
            if(npage !== 0)
                setNumbers2([npage - 3, npage - 2, npage - 1, npage].slice(1));
        }else if(page === 4){
            setNumbers1([0, 1, 2, 3, 4, 5, 6].slice(1));
            if(npage !== 0)
                setNumbers2([npage - 3, npage - 2, npage - 1, npage].slice(1));
        }else if(page === npage){
            setNumbers1([0, 1, 2, 3].slice(1));
            if(npage !== 0)
                setNumbers2([npage - 2, npage - 1, npage].slice(1));
        }else if(page === npage - 1){
            setNumbers1([0, 1, 2, 3].slice(1));
            if(npage !== 0)
                setNumbers2([page - 3, page - 2, page - 1, page, page + 1].slice(1));
        }else if(page === npage - 2){
            setNumbers1([0, 1, 2, 3].slice(1));
            if(npage !== 0)
                setNumbers2([page - 3, page - 2, page - 1, page, page + 1, page + 2].slice(1));
        }else if(page === npage - 3){
            setNumbers1([0, 1, 2, 3].slice(1));
            if(npage !== 0)
                setNumbers2([page - 3, page - 2, page - 1, page, page + 1, page + 2].slice(1));
        }else if(page == npage - 4){
            setNumbers1([0, 1, 2, 3].slice(1));
            if(npage !== 0)
                setNumbers2([page - 3, page - 2, page - 1, page, page + 1, page + 2, page + 3, page + 4].slice(1));
        }
        else{
            setNumbers1([0, 1, 2, 3, '...', page - 2, page - 1, page, page + 1, page + 2].slice(1));
            if(npage !== 0)
                setNumbers2([npage - 3, npage - 2, npage - 1, npage].slice(1));
        }
        setLoading(true);
        const result = await axios.get(`${myConstClass.SERVER_LINK}/leagues/number-of-nations-that-supports-league/pagination/${page - 1}/${recordsPerPage}`);
        setLeagues(result.data.content);
        setLoading(false);
    }

    const loadLeagues=async()=>{
        setLoading(true);
        const result = await axios.get(`${myConstClass.SERVER_LINK}/leagues/number-of-nations-that-supports-league/pagination/${currentPage - 1}/${recordsPerPage}`);
        setNPages(result.data.totalPages);
        setLeagues(result.data.content);
        setLoading(false);
        const lastpage = result.data.totalPages;
        setNumbers2([lastpage - 3, lastpage - 2, lastpage - 1, lastpage].slice(1));
    }

    function sortNationalities() { 
        const sortedData = [...leagues].sort((a,b) =>{
            return a.counter - b.counter;
        });
        setLeagues(sortedData);
    }

    return (
        <div>
            {
                !loading ?
                    <div className='container'>
                        <div className='py-4'>
                            <div className='mb-2'>
                            <button className='btn btn-outline-primary mx-1' onClick={sortNationalities}>Sort Ascending</button>
                            <Link className='btn btn-outline-danger mx-1' to={"/leagues"}>Back</Link>
                            </div>
                            <table className="table border shadow">
                                <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">League Name</th>
                                        <th scope="col">Region</th>
                                        <th scope="col">Number of Nationalities that supports it</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    {
                                        leagues.map((league, index) => (
                                            <tr>
                                                <th scope="row" key={index}>{index + 1}</th>
                                                <td>{league.leagueName}</td>
                                                <td>{league.region}</td>
                                                <td>{league.counter}</td>
                                            </tr>
                                        ))
                                    }
                                </tbody>
                            </table>
                            <nav>
                                <ul className='pagination'>
                                    <li className='page-item'>
                                        <a href='#' className='page-link'
                                        onClick={prePage}
                                        >Prev
                                        </a>
                                    </li>
                                    {
                                        numbers1.map((n, i) => (
                                            <li className={`page-item ${currentPage === n && n != '...' ? 'active' : ''}`} key={i}>
                                                <a href='#' className='page-link'
                                                onClick={() => changeCPage(n)}>{n}</a>
                                            </li>
                                        ))
                                    }

                                    <li className='page-item'>
                                        <a href='#' className='page-link'>...</a>
                                    </li>
                                    
                                    {   
                                        numbers2.map((n, i) => (
                                            <li className={`page-item ${currentPage === n ? 'active' : ''}`} key={i}>
                                                <a href='#' className='page-link'
                                                onClick={() => changeCPage(n)}>{n}</a>
                                            </li>
                                        ))
                                    }
                                    <li className='page-item'>
                                        <a href='#' className='page-link'
                                        onClick={nextPage}
                                        >Next
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                    :
                    <Loading />
            }
        </div>
  )

  function prePage(){
    console.log(currentPage);
    if(currentPage > 1){
        setCurrentPage(currentPage - 1);
        loadRaportWithPage(currentPage - 1);
    }
  }

  function changeCPage(id) { 
    if(id !== '...'){
        setCurrentPage(id);
        loadRaportWithPage(id);
    }
  }

  function nextPage() { 
    console.log(currentPage);
    if(currentPage < npage) {
        setCurrentPage(currentPage + 1);
        loadRaportWithPage(currentPage + 1);
    }
  }
}
