import React, { useEffect, useState } from 'react';
import './Popup.css';

const CreatePopup = ({ onClose, mode }) => {
    const [currentRow, setCurrentRow] = useState([]);
    const [dropdownOptions, setDropdownoptions] = useState([]);

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

    const handleInputChange = (e, index) => {
        const newRow = [...currentRow];
        newRow[index] = e.target.value;
        setCurrentRow(newRow);

    };

    const resetChanges = () => new Promise(resolve => {
        setCurrentRow([]);
        resolve();
    });

    const handleClick = async () => {
        await resetChanges();
        onClose();
    };


    const handleCreate = async () => {
        if(mode ==='License'){

            console.log("Fetch start mode license");
            

        await fetch(`http://localhost:8080/addLicense/${currentRow[0]}/${currentRow[2]}/${currentRow[3]}/${currentRow[4]}/${currentRow[5]}/${currentRow[1]}`, {
            method: "Post",
            headers: {
                'Authorization': 'Basic ' + btoa('Bart:123')
            }
        });
// /addLicensingWithFrontend/{email}/{department}/{company}/{licenseID}
        }else{

            await fetch(`http://localhost:8080/addLicensingWithFrontendNew/${currentRow[0]}/${currentRow[1]}/${currentRow[2]}/${currentRow[3][0]}/${currentRow[3][1]}/${currentRow[3][2]}`, { //ändern
                method: "Post",
                headers: {
                    'Authorization': 'Basic ' + btoa('Bart:123')
                }
            });
        }

        console.log("current ror: ", currentRow[3]);
        console.log("current ror: ", currentRow[3][0]);
        console.log("current ror: ", currentRow[3][1]);
        console.log("current ror: ", currentRow[3][2]);

        onClose();
        window.location.reload();
    }
    

    const renderInputs = () => {
        return (
            <>
                <input className='testInput' defaultValue={""} onChange={(e) => handleInputChange(e, 0)} />
                <input defaultValue={""}  onChange={(e) => handleInputChange(e, 1)} type={mode === 'License' ? 'Date' : 'text'}  />
                <input defaultValue={""} onChange={(e) => handleInputChange(e, 2)} type={mode === 'License' ? 'Date' : 'text'} />
                                
                {mode !== 'License' && (
                    <select name="LicenseOptions" id="LicenseOptions" onChange={(e) => {
                        const selectedOption = dropdownOptions.find(option => option[0] === e.target.value);
                        handleInputChange({ target : { value: selectedOption } }, 3)
                        }} >
                            <option></option>
                        {dropdownOptions.map((option, index) => (
                            <option key={index} value={option[0]}> {option[0]} | PO: {option[1]} | amount: {option[2]} </option>
                        ))}
                    </select>
                )}
                
                {mode === 'License' && <input defaultValue={""} onChange={(e) => handleInputChange(e, 3)} />}

                {mode === 'License' && <input defaultValue={""} onChange={(e) => handleInputChange(e, 4)} />}
                {mode === 'License' && <input defaultValue={""} onChange={(e) => handleInputChange(e, 5)} />}

               
            </>
        );
    };

    return (
        <div id="PopupMainDiv">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-x-circle" viewBox="0 0 16 16" onClick={handleClick}>
                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16" />
                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708" />
            </svg>
            <h2>Create {mode === 'License' ? 'License' : 'Licensing'}</h2>
            <div id='divLicenseDetailsTitle'>
                {mode === 'License' ? (
                    <>
                        
                        <p><strong>Amount</strong></p>
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
                        {/* <p><strong>Subscription Pack</strong></p>
                        <p><strong>Expiration Date</strong></p> */}
                        <p><strong>ID (PO (new))</strong></p>
                        {/* <p><strong>PO (old)</strong></p> */}
                    </>
                )}
            </div>
            <div id='divLicenseDetails'>
                {renderInputs()}
            </div>
            <div id='PopupButtons'>
                
                <p className="DeleteButton" onClick={handleCreate}>Create</p>
            </div>
        </div>
    );
};

export default CreatePopup;
