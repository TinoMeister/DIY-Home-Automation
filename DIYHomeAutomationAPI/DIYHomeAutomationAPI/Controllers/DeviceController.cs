using DIYHomeAutomationAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using NuGet.Protocol.Plugins;
using System.Reflection;

namespace DIYHomeAutomationAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DeviceController : ControllerBase
    {
        // variables
        private readonly SensorDbContext _context;

        public DeviceController(SensorDbContext context) => _context = context;

        /// <summary>
        /// This method search in the database all the Devices.
        /// </summary>
        /// <returns>List of devices</returns>
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Device>>> GetDevices() =>
            // Return an List with all the Devices
            await _context.Devices.ToListAsync();

        /// <summary>
        /// This method search in the database all the Devices with the same Room's id
        /// </summary>
        /// <param name="roomId">Room's Id</param>
        /// <returns>List of devices</returns>
        [HttpGet("{roomId}")]
        public async Task<ActionResult<IEnumerable<Device>>> GetDevices(int roomId) =>
            // Get an List with all the devices that have the same room Id
            await _context.Devices.Where(s => s.RoomId == roomId).ToListAsync();

        /// <summary>
        /// This method creates a new device
        /// </summary>
        /// <param name="device">Device objetct</param>
        /// <returns>Method result</returns>
        [HttpPost]
        public async Task<ActionResult<Device>> PostDevice(Device device)
        {
            //Verify if the sensor receibed is not null
            if (device is null)
                BadRequest();

            // Add the device to an entity entry to insert into the database
            _context.Devices.Add(device);

            // Try to save the entry to the database
            await _context.SaveChangesAsync();

            // If is successfully then returns the last Device inserted
            return await _context.Devices.OrderByDescending(x => x.Id).FirstAsync();
        }

        /// <summary>
        /// This method updates a Device
        /// </summary>
        /// <param name="id">Device's Id</param>
        /// <param name="device">Device object</param>
        /// <returns>Method Result</returns>
        [HttpPut("{id}")]
        public async Task<ActionResult> PutDevice(int id, Device? device)
        {
            // Verify if the device receibed is not null or if the Device id are the same
            if (device is null || id != device.Id)
                return BadRequest();

            // Put the device as an entry an set the state as modified to update the database
            _context.Entry(device).State = EntityState.Modified;

            // Try to save to database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }

        /// <summary>
        /// This method removes a Device from the database
        /// </summary>
        /// <param name="id">Device Id</param>
        /// <returns>Method Result</returns>
        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteDevice(int id)
        {
            // Verify if there are any devices in the database, if not then return BadRequest()
            if (_context.Devices.IsNullOrEmpty())
                BadRequest();

            // Get the device by the id
            Device? device = await _context.Devices.FindAsync(id);

            // Send the device to the method DeleteUser 
            return await DeleteDevice(device);
        }

        /// <summary>
        /// This method removes an Device from the database
        /// </summary>
        /// <param name="device>Device Object</param>
        /// <returns>Method Result</returns>
        [HttpDelete]
        public async Task<ActionResult> DeleteDevice(Device? device)
        {
            // Verify if the are any devices in the database or if the device is null, if not then return BadRequest()
            if (_context.Devices.IsNullOrEmpty() || device is null)
                BadRequest();

            // Put the device as an entry an set the state as remove from database
            _context.Devices.Remove(device);

            // Try to save to database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }
    }
}
