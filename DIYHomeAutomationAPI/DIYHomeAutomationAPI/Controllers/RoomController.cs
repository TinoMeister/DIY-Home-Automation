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
    public class RoomController : ControllerBase
    {
        // variables
        private readonly SensorDbContext _context;

        public RoomController(SensorDbContext context) => _context = context;

        /// <summary>
        /// This method search in the database all the Rooms.
        /// </summary>
        /// <param name="userId">User's Id</param>
        /// <returns>List with all the Rooms</returns>
        [HttpGet("{userId}")]
        public async Task<ActionResult<IEnumerable<Room>>> GetRooms(string userId) =>
            await _context.Rooms.Where(r => r.UserId.Equals(userId)).ToListAsync();

        /// <summary>
        /// This method creates a new Room
        /// </summary>
        /// <param name="room"></param>
        /// <returns></returns>
        [HttpPost]
        public async Task<ActionResult<Room>> PostRoom(Room room)
        {
            // Verify if the room receibed is not null
            if (room is null) 
                return BadRequest();

            // Add the room to an entity entry to insert into the database
            _context.Rooms.Add(room);

            // Try to save the entry to the database
            await _context.SaveChangesAsync();

            // If is successfully then returns the last Room Inserted
            return await _context.Rooms.OrderByDescending(r => r.Id).FirstAsync();
        }

        /// <summary>
        /// This method updates an Room
        /// </summary>
        /// <param name="id">Room's Id</param>
        /// <param name="room">Room Object</param>
        /// <returns>Method Result</returns>
        [HttpPut("{id}")]
        public async Task<ActionResult> PutRoom(int id, Room room)
        {
            // Verify if the room receibed is not null or if the room id are the same
            if (room is null || id != room.Id)
                return BadRequest();

            // Put the room as an entry an set the state as modified to update the database
            _context.Entry(room).State = EntityState.Modified;

            // Try to save to database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }

        /// <summary>
        /// This method removes an Room from the database
        /// </summary>
        /// <param name="id">Room's Id</param>
        /// <returns>Method Result</returns>
        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteRoom(int id)
        {
            // Verify if the are any Rooms in the database, if not then throw exception
            if (_context.Rooms.IsNullOrEmpty())
                return BadRequest();

            // Get the room by the id
            Room? room = await _context.Rooms.FindAsync(id);

            // Send the room to the method DeleteRoom 
            return await DeleteRoom(room);
        }

        /// <summary>
        /// This method removes an Room from the database
        /// </summary>
        /// <param name="room">Room Object</param>
        /// <returns>Method Result</returns>
        [HttpDelete]
        public async Task<ActionResult> DeleteRoom(Room? room)
        {
            // Verify if the are any Rooms in the database or if the room is null, if not then return NotFound
            if (_context.Rooms.IsNullOrEmpty() || room is null)
                return BadRequest();

            using (var transaction = _context.Database.BeginTransaction())
            {
                try
                {
                    // Get all devices
                    var devices = _context.Devices.Where(d => d.RoomId.Equals(room.Id)).ToList();

                    foreach (var device in devices)
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
                    }

                    // Delete from Devices
                    _context.Devices.RemoveRange(devices);

                    // Try to save to database
                    await _context.SaveChangesAsync();

                    // Delete from Rooms
                    _context.Rooms.Remove(room);

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