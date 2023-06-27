import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import * as myConstClass from '../constants/constants';
import Loading from '../constants/Loading';

export default function NumberNationalities() {

    const[loading, setLoading] = useState(false);
    const[raports, setRaport] = useState([]);

    const[currentPage, setCurrentPage] = useState(1);
    const[npage, setNPages] = useState(0);
    const[numbers, setNumbers] = useState([]);
    const recordsPerPage = 50;


    useEffect(() => {
        loadRaport();
    }, []);

    const loadRaportWithPage=async(page)=>{
        setLoading(true);
        const result = await axios.get(`${myConstClass.SERVER_LINK}/fans/numberNations/pagination/${page - 1}/${recordsPerPage}`);
        setRaport(result.data.content);
        setLoading(false);
    }

    const loadRaport=async()=>{
        setLoading(true);
        const result = await axios.get(`${myConstClass.SERVER_LINK}/fans/numberNations/pagination/${currentPage - 1}/${recordsPerPage}`);
        setNPages(result.data.totalPages);
        setRaport(result.data.content);
        setLoading(false);
        const lastpage = result.data.totalPages;
        setNumbers([...Array(lastpage + 1).keys()].slice(1));
    }

    return (
        <div>
            {
                !loading ?
                <div className='container'>
                    <div className='py-4'>
                        <div className='mb-2'>
                        <Link className='btn btn-outline-danger mx-1' to={"/fans"}>Back</Link>
                        </div>
                        <table className="table border shadow">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Nationality</th>
                                    <th scope="col">Counter</th>
                                </tr>
                            </thead>
                            <tbody>

                                {
                                    raports.map((raport, index) => (
                                        <tr>
                                            <th scope="row" key={index}>{index + 1}</th>
                                            <td>{raport.nationality}</td>
                                            <td>{raport.counter}</td>
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
                                    numbers.map((n, i) => (
                                        <li className={`page-item ${currentPage === n && n !== '...' ? 'active' : ''}`} key={i}>
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
    if(currentPage < npage) {
        setCurrentPage(currentPage + 1);
        loadRaportWithPage(currentPage + 1);
    }
  }
}
