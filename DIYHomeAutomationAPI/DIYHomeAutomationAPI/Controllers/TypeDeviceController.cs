using DIYHomeAutomationAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;

namespace DIYHomeAutomationAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TypeDeviceController : ControllerBase
    {
        // variables
        private readonly SensorDbContext _context;

        public TypeDeviceController(SensorDbContext context) => _context = context;

        /// <summary>
        /// This method search in the database all the Device types.
        /// </summary>
        /// <returns>List with all the device types</returns>
        [HttpGet]
        public async Task<ActionResult<IEnumerable<TypeDevice>>> GetTypeDevices() =>
            await _context.TypeDevices.ToListAsync();

        /// <summary>
        /// This method creates a new Device type
        /// </summary>
        /// <param name="typeDevice">Device type Object</param>
        /// <returns>Method Result</returns>
        [HttpPost]
        public async Task<ActionResult<TypeDevice>> PostDeviceType(TypeDevice typeDevice)
        {
            // Verify if the device type receibed is not null
            if (typeDevice is null)
                return BadRequest();

            // Add the device type to an entity entry to insert into the database
            _context.TypeDevices.Add(typeDevice);

            // Try to save the entry to the database
            await _context.SaveChangesAsync();

            // If is successfully then returns the last device type Inserted
            return await _context.TypeDevices.OrderByDescending(t => t.Id).FirstAsync();
        }

        /// <summary>
        /// This method updates an device type
        /// </summary>
        /// <param name="id">device type's Id</param>
        /// <param name="typeDevice">device type Object</param>
        /// <returns>Method Result</returns>
        [HttpPut("{id}")]
        public async Task<ActionResult> PutDeviceType(int id, TypeDevice typeDevice)
        {
            // Verify if the device type receibed is not null or if the device type id are the same
            if (typeDevice is null || id != typeDevice.Id)
                return BadRequest();

            // Put the device type as an entry an set the state as modified to update the database
            _context.Entry(typeDevice).State = EntityState.Modified;

            // Try to save to database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }

        /// <summary>
        /// This method removes an device type from the database
        /// </summary>
        /// <param name="id">device type's Id</param>
        /// <returns>Method Result</returns>
        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteDeviceType(int id)
        {
            // Verify if the are any device type in the database, if not then return BadRequest
            if (_context.TypeDevices.IsNullOrEmpty())
                return BadRequest();

            // Get the device type by the id
            TypeDevice? typeDevice = await _context.TypeDevices.FindAsync(id);

            // Send the device type to the method DeleteDeviceType
            return await DeleteDeviceType(typeDevice);
        }

        /// <summary>
        /// This method removes an device type from the database
        /// </summary>
        /// <param name="typeDevice">device type Object</param>
        /// <returns>Method Result</returns>
        [HttpDelete]
        public async Task<ActionResult> DeleteDeviceType(TypeDevice? typeDevice)
        {
            // Verify if the are any device type in the database or if the device type is null, if not then return NotFound
            if (_context.TypeDevices.IsNullOrEmpty() || typeDevice is null)
                return NotFound();

            // Put the device type as an entry an set the state as remove from database
            _context.TypeDevices.Remove(typeDevice);

            // Try to save to database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }
    }
}
