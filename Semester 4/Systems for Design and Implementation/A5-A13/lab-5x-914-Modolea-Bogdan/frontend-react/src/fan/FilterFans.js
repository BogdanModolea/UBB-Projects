import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import Loading from '../constants/Loading';
import * as myConstClass from '../constants/constants';


export default function FilterFans() {
    const[fans, setFans] = useState([]);
    const[loading, setLoading] = useState(false);
    const {age} = useParams();

    const[currentPage, setCurrentPage] = useState(1);
    const[npage, setNPages] = useState(0);
    const[numbers1, setNumbers1] = useState([0, 1, 2, 3].slice(1));
    const[numbers2, setNumbers2] = useState([]);
    const recordsPerPage = 100;

    useEffect(() => {
        loadFans();
    }, []);

    const loadFansWithPage=async(page)=>{
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
        const result = await axios.get(`${myConstClass.SERVER_LINK}/fans/filter/${age}/pagination/${page - 1}/${recordsPerPage}`);
        setFans(result.data.content);
        setLoading(false);
    }

    const loadFans=async()=>{
        try{
            const result = await axios.get(`${myConstClass.SERVER_LINK}/fans/filter/${age}/pagination/${currentPage - 1}/${recordsPerPage}`);
            setFans(result.data.content);
            setNPages(result.data.totalPages);
            const lastpage = result.data.totalPages;
            setNumbers2([lastpage - 3, lastpage - 2, lastpage - 1, lastpage].slice(1));
        }
        catch(err){
            console.log(err);
        }
    }

    return (
        <div>
            {
                !loading ?
                    <div className='container'>
                        <div className='py-4'>
                            <div className='py-2'>
                                <Link className='btn btn-outline-danger mx-1' to={"/fans"}>Back</Link>
                            </div>
                            <table className="table border shadow">
                                <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Name</th>
                                        <th scope="col">Age</th>
                                        <th scope="col">Nationality</th>
                                        <th scope="col">Occupation</th>
                                        <th scope="col">Place Of Birth</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    {
                                        fans.map((fan, index) => (
                                            <tr>
                                                <th scope="row" key={index}>{index + 1}</th>
                                                <td>{fan.name}</td>
                                                <td>{fan.age}</td>
                                                <td>{fan.nationality}</td>
                                                <td>{fan.occupation}</td>
                                                <td>{fan.placeOfBirth}</td>
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
    if(currentPage > 1){
        setCurrentPage(currentPage - 1);
        loadFansWithPage(currentPage - 1);
    }
  }

  function changeCPage(id) { 
    if(id !== '...'){
        setCurrentPage(id);
        loadFansWithPage(id);
    }
  }

  function nextPage() { 
    if(currentPage < npage) {
        setCurrentPage(currentPage + 1);
        loadFansWithPage(currentPage + 1);
    }
  }   
}
