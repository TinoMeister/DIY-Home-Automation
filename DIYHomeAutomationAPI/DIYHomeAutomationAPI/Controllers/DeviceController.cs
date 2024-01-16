using DIYHomeAutomationAPI.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;

namespace DIYHomeAutomationAPI.Controllers
{
    [Authorize]
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
        /// <param name="userId">User's Id</param>
        /// <returns>List of devices</returns>
        [HttpGet("{userId}")]
        public async Task<ActionResult<IEnumerable<Device>>> GetDevices(string userId) =>
            await (
                from r in _context.Rooms
                join d in _context.Devices on r.Id equals d.RoomId
                where r.UserId == userId
                select d
            ).ToListAsync();

        /// <summary>
        /// This method search in the database all the Devices that are enabled.
        /// </summary>
        /// <param name="userId">User's Id</param>
        /// <returns>List of devices</returns>
        [HttpGet("Enabled/{userId}")]
        public async Task<ActionResult<IEnumerable<Device>>> GetDevicesEnabled(string userId) =>
            await (
                from r in _context.Rooms
                join d in _context.Devices on r.Id equals d.RoomId
                where r.UserId == userId && d.State == true
                select d
            ).ToListAsync();

        /// <summary>
        /// This method search in the database all the Devices with the same Room's id
        /// </summary>
        /// <param name="roomId">Room's Id</param>
        /// <returns>List of devices</returns>
        [HttpGet("Room/{roomId}")]
        public async Task<ActionResult<IEnumerable<Device>>> GetDevicesRoom(int roomId) =>
            await _context.Devices.Where(s => s.RoomId == roomId).ToListAsync();

        /// <summary>
        /// This method search in the database all the devices with the same Device's name
        /// </summary>
        /// <param name="espName">Esp's Name</param>
        /// <returns>List of devices</returns>
        [AllowAnonymous]
        [HttpGet("Esp/{espName}")]
        public async Task<ActionResult<IEnumerable<Device>>> GetDevicesEsp(string espName) => 
            await (
                from e in _context.Esps
                join r in _context.Rooms on e.Id equals r.EspId
                join d in _context.Devices on r.Id equals d.RoomId
                where e.Name == espName
                select d
            ).ToListAsync();

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
                return BadRequest();

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
                return BadRequest();

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
                return BadRequest();

            using (var transaction = _context.Database.BeginTransaction())
            {
                try
                {
                    // Delete from TaskDevices
                    var taskDevices = _context.TaskDevices.Where(td => td.DeviceId.Equals(device.Id)).ToList();
                    _context.TaskDevices.RemoveRange(taskDevices);

                    // Delete from Notifications
                    var notifications = _context.Notifications.Where(n => n.DeviceId.Equals(device.Id)).ToList();
                    _context.Notifications.RemoveRange(notifications);

                    // Delete from Restrictions
                    var restrictions = _context.Restrictions.Where(r => r.PrimaryDeviceId.Equals(device.Id) ||
                    r.SecondaryDeviceId.Equals(device.Id)).ToList();
                    _context.Restrictions.RemoveRange(restrictions);

                    // Delete from Histories
                    var histories = _context.Histories.Where(h => h.DeviceId.Equals(device.Id)).ToList();
                    _context.Histories.RemoveRange(histories);

                    // Try to save to database
                    await _context.SaveChangesAsync();

                    // Delete from Devices
                    _context.Devices.Remove(device);

                    // Try to save to database
                    await _context.SaveChangesAsync();
                    transaction.Commit();
                }
                catch (Exception ex)
                {
                    transaction.Rollback();
                    // Handle the exception
                    return BadRequest($"Error: {ex.Message}");
                }
            }

            // If is successfully then returns an OK
            return Ok();
        }
    }
}
