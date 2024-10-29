import EditButton from "./EditButton";
import {useState } from 'react';
import './TableLicenseRow.css'

const TableRow = ({ row, onEdit, mode}) => {

    let idLIcensing = null;

    if (mode !== 'License') {
        idLIcensing = row[7];
        
    }

     
    let backgroundColorClass = "";
 
    const today = new Date();
    const expirationDate = new Date(mode === "License" ? row[3] : row[4]);


   
    const difference = (expirationDate.getFullYear() - today.getFullYear()) * 12 + (expirationDate.getMonth() - today.getMonth());

    
   
    if (today >= expirationDate) {
        
        backgroundColorClass = "expired";
    } else if (difference < 3) {
        
        backgroundColorClass = "almost-expired";
    } 

    


    const commonCells = [
        <td key={0} className={backgroundColorClass}>{row[0]}</td>,
        <td key={1} className={backgroundColorClass}>{row[1]}</td>,
        <td key={2} className={backgroundColorClass}>{row[2]}</td>,
        mode === 'License' && <td key={7} className={backgroundColorClass}>{row[7]}</td>,
        <td key={3} className={backgroundColorClass}>{row[3]}</td>,
        <td key={4} className={backgroundColorClass}>{row[4]}</td>,
        <td key={5} className={backgroundColorClass}>{row[5]}</td>,
        <td key={6} className={backgroundColorClass}>{row[6]}</td>
    ].filter(Boolean);

    return (
        <tr>
            {commonCells}
            <EditButton onClick={() => onEdit(row, idLIcensing)} />
        </tr>
    );
};


export default TableRow;


