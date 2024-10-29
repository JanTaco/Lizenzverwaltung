import React, { useState, useEffect } from 'react';
import './Popup.css';

const Popup = ({ row, onClose, mode, licensingID}) => {
    const [originalRow] = useState(row ? [...row] : []);
    const [currentRow, setCurrentRow] = useState(row ? [...row] : []);
    const [dropdownOptions, setDropdownoptions] = useState([]);
    console.log("licensingID bwi Popup: ", licensingID);
   


    
    

    useEffect(() => {
        const fetchLincenseInformation = async () => {
            try{
                const response = await fetch(`http://localhost:8080/testLicenseInformation`, {
                    headers: {
                        'Authorization': 'Basic ' + btoa('Bart:123')
                    }
                });
                const data = await response.json();
                console.log("data: ", data);
                setDropdownoptions(data);
            }catch (error){
                console.error('Error fetching license information: ', error);
            }
        };

        fetchLincenseInformation();

        
    }, []);

    if (!row) return null;

    const handleInputChange = (e, index) => {
        const newRow = [...currentRow];
        newRow[index] = e.target.value;
        setCurrentRow(newRow);


        // console.log("Popup bearbeiten: ", currentRow[3]);
        // console.log("Popup bearbeiten: ", currentRow[3][0]);
        // console.log("Popup bearbeiten: ", currentRow[3][1]);
        // console.log("Popup bearbeiten: ", currentRow[3][2]);
    };



    const resetChanges = () => new Promise(resolve => {
        setCurrentRow([...originalRow]);
        resolve();
    });

    const handleClick = async () => {
        await resetChanges();
        onClose();
    };

    const handleUpdate = async () => {
        if (mode === 'License') { 
            
            
        await fetch(`http://localhost:8080/updateLicense/${currentRow[0]}/${currentRow[1]}/${currentRow[7]}/${currentRow[3]}/${currentRow[4]}/${currentRow[5]}/${currentRow[6]}`, {
            method: "PUT",
            headers: {
                'Authorization': 'Basic ' + btoa('Bart:123')
            }
        });
        


        } else { // änderung der Lizenzvergabe ändern!!!!!!!!!!!!!
           // http://localhost:8080/updateEmployee/5/test3U@gmail.com/IT/Vodafone/UpdateTest/444/220
           console.log("folgendes funktioniert: http://localhost:8080/updateEmployee/5/test3U@gmail.com/IT/Vodafone/UpdateTest/444/220")
           console.log("Fetch:  "+`${licensingID}/${currentRow[0]}/${currentRow[1]}/${currentRow[2]}/${currentRow[3][0]}/${currentRow[3][1]}/${currentRow[3][2]}`);
            await fetch(`http://localhost:8080/updateEmployee/${licensingID}/${currentRow[0]}/${currentRow[1]}/${currentRow[2]}/${currentRow[3][0]}/${currentRow[3][1]}/${currentRow[3][2]}`, {
                method: "PUT",
                headers: {
                    'Authorization': 'Basic ' + btoa('Bart:123')
                }
            });
            
        }

        onClose();
        window.location.reload();
    };




    const handleDelete = async () => {

        if(mode === 'License'){ 

            console.log('License löschung beginnt');

        await fetch(`http://localhost:8080/deleteLicense/${currentRow[0]}`, {
            method: "DELETE",
            headers: {
                'Authorization': 'Basic ' + btoa('Bart:123')
            }
        });
        

        }else{

            await fetch(`http://localhost:8080/deleteLicensing/${licensingID}`, {
                method: "DELETE",
                headers: {
                    'Authorization': 'Basic ' + btoa('Bart:123')
                }
            });
            
        }

        onClose();
        window.location.reload();
    }
// disabled={mode === 'license}
    const renderInputs = () => {
        // console.log("Current row bei render: ", currentRow);
        // console.log("originalRowbei render: ", originalRow);
        // console.log("dropdownOptions render: ", dropdownOptions);

        // PO: {originalRow[5]} |
        return (
            <>
               
                <input className='testInput' value={currentRow[0]} onChange={(e) => handleInputChange(e, 0)} disabled={mode === 'License'}/>
                <input value={currentRow[1]} onChange={(e) => handleInputChange(e, 1)} />
                <input value={currentRow[2]} onChange={(e) => handleInputChange(e, 2)} disabled={mode === 'License'}/>
                {mode === 'License' && <input value={currentRow[7]} onChange={(e) => handleInputChange(e, 7)} />}
           

                {mode !== 'License' && (
                    <select name="LicenseOptions" id="LicenseOptions" onChange={(e) => {
                        const selectedOption = dropdownOptions.find(option => option[0] === e.target.value);
                        handleInputChange({ target : { value: selectedOption } }, 3)
                        }} >
                            <option>{originalRow[3]} | amount: 22</option> 
                        {dropdownOptions.map((option, index) => (
                            <option key={index} value={option[0]}> {option[0]} | PO: {option[1]} | amount: {option[2]} </option>
                        ))}
                    </select>
                )}
                
                {mode === 'License' && <input value={currentRow[3]} onChange={(e) => handleInputChange(e, 3)} disabled={mode !== 'License'}/>}

                
                
                <input value={currentRow[4]} onChange={(e) => handleInputChange(e, 4)} disabled={mode !== 'License'}/>
                <input value={currentRow[5]} onChange={(e) => handleInputChange(e, 5)} disabled={mode !== 'License'}/>
                <input value={currentRow[6]} onChange={(e) => handleInputChange(e, 6)} disabled={mode !== 'License'}/>
                
            </>
        );
    };

    return (
        <div id="PopupMainDiv">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-x-circle" viewBox="0 0 16 16" onClick={handleClick}>
                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
            </svg>
            <h2>Edit {mode === 'License' ? 'License' : 'Licensing'}</h2>
            <div id='divLicenseDetailsTitle'>
                {mode === 'License' ? (
                    <>
                        <p><strong>ID</strong></p>
                        <p><strong>Amount</strong></p>
                        <p><strong>Available</strong></p>
                        <p><strong>Start Date</strong></p>
                        <p><strong>Expiration Date</strong></p>
                        <p><strong>PO (new)</strong></p>
                        <p><strong>PO (old)</strong></p>
                        <p><strong>Subscription Pack</strong></p>
                    </>
                ) : (
                    <>
                        <p><strong>E-Mail</strong></p>
                        <p><strong>Department</strong></p>
                        <p><strong>Company</strong></p>
                        <p id='ueberschriftSubscriptionPack'><strong>Subscription Pack</strong></p>
                        <p><strong>Expiration Date</strong></p>
                        <p><strong>PO (new)</strong></p>
                        <p><strong>PO (old)</strong></p>
                    </>
                )}
            </div>
            <div id='divLicenseDetails'>
                {renderInputs()}
            </div>
            <div id='PopupButtons' >
                <p id='UpdateButton' onClick={handleUpdate}>Update</p>
                <p className="DeleteButton" onClick={handleDelete}>Delete</p>
            </div>
        </div>
    );
};

export default Popup;
