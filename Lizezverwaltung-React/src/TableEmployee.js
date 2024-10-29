import './Table.css';
import TableLicenseRow from './TableLicenseRow';
import { useEffect, useState } from 'react';
import Popup from './Popup';

const TableEmployee = () => {

   
    const [data, setData] = useState([]);
    const [selectedRow, setSelectedRow] = useState(null); // Zustand für die ausgewählte Zeile
    const [selectedIdLicensing, setSelectedIdLicensing] = useState(null);
    

    var url = '';

    if(localStorage.getItem('showExpiredLicenses') === "true"){
        
        url = 'http://localhost:8080/allLicensingExpiringSoon';
    }else{
        
        url = 'http://localhost:8080/allLicensing';
    }


    useEffect(() => {
        fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': 'Basic ' + btoa('Bart:123')
            }
        })
        .then(response => response.json())
        .then(data => setData(data))
        .catch(error => console.error('Error fetching data:', error));
    }, []);

    const handleEditClick = (row, idLicensing) => {
        console.log("jetzt wird die funktion ausgeführt");
        console.log("idLIcensing: ", idLicensing);

        setSelectedRow(row); 
        setSelectedIdLicensing(idLicensing);

       
    };



    const closePopup = () => {
        console.log("selectedIdLicensing VORHER: ", selectedIdLicensing);

        setSelectedRow(null); // Popup schließen
        setSelectedIdLicensing(null);

   
    };

    // useEffect um den neuen Wert zu überwachen
    useEffect(() => {
        console.log("Neuer Wert von selectedIdLicensing: ", selectedIdLicensing);
    }, [selectedIdLicensing]);

    return (
        <>
            <table>
                <thead>
                    <tr> 
                        <th>E-Mail</th>
                        <th>Department</th>
                        <th>Company</th>
                        <th>Subscription Pack</th>
                        <th>Expiration Date</th>
                        <th>PO (new)</th>
                        <th>PO (old)</th>
                        
                    </tr>
                </thead>

                <tbody>
                    {data.map((row, index) => (
                        <TableLicenseRow key={index} row={row} onEdit={handleEditClick} />
                    ))}
                </tbody>
            </table>

            {selectedRow && <Popup row={selectedRow} onClose={closePopup} mode={"Employee"} licensingID={selectedIdLicensing}/>}
        </>
    );
    
}
 
export default TableEmployee;